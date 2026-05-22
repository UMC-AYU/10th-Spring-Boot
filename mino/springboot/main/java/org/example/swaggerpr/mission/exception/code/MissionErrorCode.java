package org.example.swaggerpr.mission.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_1",
            "Mission not found.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
