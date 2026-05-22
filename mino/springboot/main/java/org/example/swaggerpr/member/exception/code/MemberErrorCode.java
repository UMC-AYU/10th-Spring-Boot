package org.example.swaggerpr.member.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,
            "MEMBER400_1",
            "Email already exists."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "Member not found.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
