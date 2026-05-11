package org.example.swaggerpr.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResDto {

    @Builder
    @Getter
    public static class MissionPreviewDto {
        private Long missionId;
        private String storeName;
        private String content;
        private Integer rewardPoint;
        private String status;
    }

    @Builder
    @Getter
    public static class MissionListDto {
        private List<MissionPreviewDto> missions;
        private Integer page;
        private Integer size;
        private Long totalElements;
        private Integer totalPages;
    }
}
