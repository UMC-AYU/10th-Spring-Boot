package com.example.springboot.domain.review.exception;

import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_CREATE_OK(HttpStatus.CREATED, "REVIEW201_1", "리뷰가 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}