package com.aim.umc10th.global.config.config;

import com.aim.umc10th.global.config.security.CustomAccessDenied;
import com.aim.umc10th.global.config.security.CustomEntryPoint;
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

@EnableWebSecurity //Spring Security 설정을 활성화시키는 역할을 한다.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final String[] allowUris = {
            //Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/auth/**"
    };

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
                        .authenticationEntryPoint(new CustomEntryPoint()) //401 에러 핸들러 매핑
                        .accessDeniedHandler(new CustomAccessDenied()) //403 에러 핸들러 매핑
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
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //PasswordEncoder는 해커가 DB를 털더라도 비밀번호는 절대 알아낼 수 없도록 강력한 해시 함수로 단방향 암호화를 해주는 장치
        return new BCryptPasswordEncoder();


    }
}
