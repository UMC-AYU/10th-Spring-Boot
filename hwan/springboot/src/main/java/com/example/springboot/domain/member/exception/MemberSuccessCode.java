package com.example.springboot.domain.member.exception;

import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MYPAGE_OK(HttpStatus.OK, "USER200_1", "마이페이지 조회 성공"),
    SIGNUP_SUCCESS(HttpStatus.CREATED, "USER201_1", "회원가입이 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}