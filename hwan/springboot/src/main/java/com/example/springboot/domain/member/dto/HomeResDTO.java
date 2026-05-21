package com.example.springboot.domain.member.dto;

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
            Long userMissionId,
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