package com.example.springboot.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class MissionResDTO {

    @Builder
    public record MissionItem(
            Long userMissionId,
            String storeName,
            String condition,
            Integer rewardPoint,
            Integer rewardPercent,
            Integer dDay,
            String status,
            Boolean hasReview
    ) {}

    @Builder
    public record MissionList(
            String status,
            List<MissionItem> missions,
            Integer page,
            Integer totalPages
    ) {}

    @Builder
    public record MissionComplete(
            Long userMissionId,
            String status,
            Integer rewardPoint,
            String message
    ) {}
}