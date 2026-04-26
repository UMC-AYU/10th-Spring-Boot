package org.example.swaggerpr.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResDto {

    @Builder
    @Getter
    public static class MissionPreviewDto {
        private Long missionId;
        private String title;
        private String content;
        private Integer reward;
        private String status;
    }

    @Builder
    @Getter
    public static class MissionListDto {
        private List<MissionPreviewDto> missions;
    }
}
