package com.aim.umc10th.domain.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class HomeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegionMissionListDTO{
        List<MissionSummaryDTO> missionList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionSummaryDTO{
        Long missionId;
        String storeNAme;
        Integer reward;
        String missionSpec;
    }
}
