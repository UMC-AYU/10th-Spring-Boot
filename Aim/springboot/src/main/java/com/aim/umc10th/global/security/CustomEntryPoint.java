package com.aim.umc10th.global.security;
import com.aim.umc10th.global.apiPayload.ApiResponse;
import com.aim.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
        throws IOException, ServletException{

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 💡 원래 잘 작동하던 로컬 생성 방식으로 복구!
        ObjectMapper objectMapper = new ObjectMapper();

        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                GeneralErrorCode.UNAUTHORIZED.getCode(),
                GeneralErrorCode.UNAUTHORIZED.getMessage(),
                null
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
