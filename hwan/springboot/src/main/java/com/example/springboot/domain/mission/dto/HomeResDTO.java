package com.example.springboot.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class HomeResDTO {

    @Builder
    public record MissionProgress(
            Integer current,
            Integer total,
            Integer bonusPoint
    ) {}

    @Builder
    public record MissionPreview(
            Long missionId,
            String storeName,
            String category,
            String condition,
            Integer rewardPoint,
            Integer dDay,
            String status
    ) {}

    @Builder
    public record HomeInfo(
            String location,
            Integer point,
            MissionProgress missionProgress,
            List<MissionPreview> missions,
            Integer page,
            Integer totalPages
    ) {}
}
