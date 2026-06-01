package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.global.enums.MissionStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResponseDTO {

    @Getter
    @Builder
    public static class MissionInfo {
        private Long missionId;
        private String title;
        private MissionStatus status;
        private String store;
    }

}