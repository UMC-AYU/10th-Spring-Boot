package com.aim.umc10th.global.config.security;

import ch.qos.logback.core.status.ErrorStatus;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
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

        ObjectMapper objectMapper = new ObjectMapper();

        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                GeneralErrorCode.UNAUTHORIZED.getCode(),
                GeneralErrorCode.UNAUTHORIZED.getMessage(),
                null
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
