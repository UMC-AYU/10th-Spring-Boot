package org.example.swaggerpr.review.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    ALREADY_EXISTS(HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "Review already exists."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "REVIEW404_1",
            "Review not found.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
