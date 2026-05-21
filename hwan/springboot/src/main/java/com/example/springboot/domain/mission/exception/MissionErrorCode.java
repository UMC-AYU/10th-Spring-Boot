package com.example.springboot.domain.mission.exception;

import com.example.springboot.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "해당 미션을 찾을 수 없습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MISSION400_1", "이미 완료된 미션입니다."),
    MISSION_FORBIDDEN(HttpStatus.FORBIDDEN, "MISSION403_1", "해당 미션에 접근 권한이 없습니다."),
    INVALID_MISSION_STATUS(HttpStatus.BAD_REQUEST, "MISSION400_2", "status는 IN_PROGRESS 또는 COMPLETED 이어야 합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}