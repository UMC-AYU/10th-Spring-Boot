package com.example.springboot.global.security.exception;

import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseSecurityHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected void writeErrorResponse(HttpServletResponse response, BaseErrorCode code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        objectMapper.writeValue(response.getOutputStream(), ApiResponse.onFailure(code, null));
    }
}