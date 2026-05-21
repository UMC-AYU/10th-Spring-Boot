package com.example.springboot.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getStatus();  // HTTP 상태코드 (404, 400 등)
    String getCode();        // 우리만의 에러 식별자 ("USER404_1")
    String getMessage();     // 사용자에게 보여줄 메시지
}