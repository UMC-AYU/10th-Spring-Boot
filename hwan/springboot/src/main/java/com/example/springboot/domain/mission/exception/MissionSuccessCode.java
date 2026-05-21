package com.example.springboot.domain.mission.exception;

import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_LIST_OK(HttpStatus.OK, "MISSION200_1", "미션 목록 조회에 성공했습니다."),
    MISSION_COMPLETE_OK(HttpStatus.OK, "MISSION200_2", "미션 성공 처리가 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}