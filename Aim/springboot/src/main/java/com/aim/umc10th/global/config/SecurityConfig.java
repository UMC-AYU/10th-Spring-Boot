package com.aim.umc10th.global.config;

import com.aim.umc10th.global.security.CustomAccessDenied;
import com.aim.umc10th.global.security.CustomEntryPoint;
import com.aim.umc10th.global.security.filter.JwtAuthFilter;
import com.aim.umc10th.global.security.service.CustomUserDetailsService;
import com.aim.umc10th.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Spring Security 설정을 활성화시키는 역할을 한다.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // [수정] 직접 new로 생성하지 않고, 스프링 컨테이너가 관리하는 빈을 주입받는다.
    private final CustomEntryPoint customEntryPoint;
    private final CustomAccessDenied customAccessDenied;

    // [9주차] JWT 필터 조립을 위해 필요한 유틸과 서비스를 주입받기
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    private final String[] allowUris = {
            //Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/auth/**"
    };

    // [9주차] JWT 필터를 스프링 컨테이너의 Bean으로 등록하고 필요한 요소를 DI 해준다.
    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    //HttpSecurity 객체를 통해 다양한 보안설정을 구성할 수 있다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomEntryPoint customEntryPoint) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 추후 JWT 인증을 사용할 예정이므로 세션을 생성하지 않도록 설정(STATELESS)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 미션 요구사항: 인증/인가 실패 시 작성한 커스텀 응답 양식(ApiResponse)으로 통일
                // 예외 상황 핸들러
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customEntryPoint) //401 에러 핸들러 매핑
                        .accessDeniedHandler(customAccessDenied) //403 에러 핸들러 매핑
                )

                //authorizeHttpRequests()는 HTTP 요청에 대한 접근 제어를 설정한다.
                .authorizeHttpRequests(requests -> requests
                        //requestMatchers() 메소드를 사용하여 특정 URL 패턴에 대한 접근 권한을 설정한다.
                        //permitAll()은 인증 없아 접근 가능한 경로를 지정한다.
                        .requestMatchers(allowUris).permitAll()
                        //anyRequest().authenticated()는 그 외 모든 요청에 대해 인증을 요구한다.
                        .anyRequest().authenticated()
                )

                //폼 기반 로그인에 대한 설정
                .formLogin(form -> form
                        // 로그인 성공하면 바로 API 테스트 할 수 있게 Swagger화면으로 강제 이동.
                        // alwaysUse를 true로 설정하여 로그인 성공 시 항상 Swagger로 리다이렉트 된다.
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        //로그인 페이지는 모든 사용자가 접근 가능하도록 설정
                        .permitAll()
                )

                //로그아웃 처리에 대한 설정
                .logout(logout -> logout
                        // /logout 경로로 로그아웃을 처리한다.
                        .logoutUrl("/logout")
                        // 로그아웃 성공시 /login?logout으로 리다이렉트 한다.
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        //기존 시큐리티 필터 체인 통로 중 UsernamePasswordAuthenticationFilter 바로 앞에 커스텀 JWT 필터를 끼워 넣기
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //PasswordEncoder는 해커가 DB를 털더라도 비밀번호는 절대 알아낼 수 없도록 강력한 해시 함수로 단방향 암호화를 해주는 장치
        return new BCryptPasswordEncoder();


    }
}
