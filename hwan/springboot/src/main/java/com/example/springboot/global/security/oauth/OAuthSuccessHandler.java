package com.example.springboot.global.security.oauth;

import com.example.springboot.global.security.entity.AuthMember;
import com.example.springboot.global.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        AuthMember authMember = (AuthMember) authentication.getPrincipal(); // 인증된 사용자 추출
        String accessToken = jwtUtil.createAccessToken(authMember.getMember().getEmail()); // JWT 발급

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(
                objectMapper.writeValueAsString(Map.of("accessToken", accessToken)) // 토큰 응답
        );
        response.getWriter().flush(); // 버퍼에 남은 데이터 강제 전송
    }
}