package org.example.swaggerpr.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResDto {

    @Builder
    @Getter
    public static class MissionPreviewDto {
        private final Long missionId;
        private final String storeName;
        private final String content;
        private final Integer rewardPoint;
        private final String status;
    }

    @Builder
    @Getter
    public static class MissionListDto {
        private final List<MissionPreviewDto> missions;
        private final Integer page;
        private final Integer size;
        private final Long totalElements;
        private final Integer totalPages;
    }

    @Builder
    @Getter
    public static class NearbyMissionDto {
        private final Long missionId;
        private final String storeName;
        private final String content;
        private final Integer rewardPoint;
    }

    @Builder
    @Getter
    public static class NearbyMissionListDto {
        private final String regionName;
        private final List<NearbyMissionDto> missions;
        private final Integer page;
        private final Integer size;
        private final Long totalElements;
        private final Integer totalPages;
    }
}
