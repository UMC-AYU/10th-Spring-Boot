package com.aim.umc10th.domain.member.code;

import com.aim.umc10th.global.config.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
       "MEMBER200_1",
               "성공적으로 유조를 조회했습니다."),
            ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
