package com.aim.umc10th.global.security;

import com.aim.umc10th.global.apiPayload.ApiResponse;
import com.aim.umc10th.global.apiPayload.code.GeneralErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAccessDenied implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException
    ) throws IOException, ServletException{

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403 Forbidden 설정

        ObjectMapper objectMapper = new ObjectMapper();

        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                GeneralErrorCode.FORBIDDEN.getCode(),
                GeneralErrorCode.FORBIDDEN.getMessage(),
                null
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
