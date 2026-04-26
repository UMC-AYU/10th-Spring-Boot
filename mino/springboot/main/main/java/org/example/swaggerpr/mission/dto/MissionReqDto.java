package org.example.swaggerpr.mission.dto;

import lombok.Getter;

public class MissionReqDto {

    @Getter
    public static class CompleteMissionDto {
        private String status;
    }
}
