package com.example.umc10th.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

public class MissionResponseDTO {

    @Getter
    @Builder
    public static class MissionInfo {
        private Long missionId;
        private String title;
        private String status;
    }
}