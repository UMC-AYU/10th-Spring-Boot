package com.example.springboot.domain.review.exception;

import com.example.springboot.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_ALREADY_EXISTS(HttpStatus.CONFLICT, "REVIEW409_1", "이미 리뷰를 작성한 가게입니다."),
    INVALID_RATING(HttpStatus.BAD_REQUEST, "REVIEW400_1", "별점은 0.5에서 5.0 사이여야 합니다."),
    INVALID_CURSOR(HttpStatus.BAD_REQUEST, "REVIEW400_2", "잘못된 커서 형식입니다."),
    INVALID_QUERY(HttpStatus.BAD_REQUEST, "REVIEW400_3", "정렬 기준은 id 또는 rating이어야 합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}