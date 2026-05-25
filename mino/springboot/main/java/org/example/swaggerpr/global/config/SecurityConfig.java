package org.example.swaggerpr.global.config;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.security.CustomAccessDeniedHandler;
import org.example.swaggerpr.global.security.CustomAuthenticationEntryPoint;
import org.example.swaggerpr.global.security.CustomUserDetailsService;
import org.example.swaggerpr.global.security.filter.JwtAuthFilter;
import org.example.swaggerpr.global.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    // JWT 없이 접근 가능한 엔드포인트 목록이다.
    // 회원가입/로그인, Swagger, H2 Console은 인증 전에 접근해야 하므로 permitAll 처리한다.
    private static final String[] PUBLIC_URLS = {
            "/auth/users/signup",
            "/auth/users/login",
            "/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/h2-console",
            "/h2-console/**"
    };

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                // JWT 기반 API 서버에서는 서버 세션과 기본 로그인 화면을 사용하지 않는다.
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                // Spring Security의 username/password 인증 필터보다 먼저 JWT를 해석해 인증 객체를 만든다.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService customUserDetailsService
    ) {
        // 필터를 직접 Bean으로 등록해 JwtUtil과 회원 조회 서비스를 주입한다.
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
