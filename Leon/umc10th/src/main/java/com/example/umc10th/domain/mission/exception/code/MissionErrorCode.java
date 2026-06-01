package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404", "미션을 찾을 수 없습니다."),
    MISSION_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "MISSION400_1", "이미 시작한 미션입니다."),
    MISSION_NOT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "MISSION400_2", "진행중인 미션이 없습니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MISSION400_3", "이미 완료된 미션입니다."),
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION404", "지역을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
