package com.example.springboot.domain.mission.converter;

import com.example.springboot.domain.mission.dto.HomeResDTO;
import com.example.springboot.domain.mission.entity.Mission;
import com.example.springboot.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class HomeConverter {

    private static final int MISSION_GOAL = 10;
    private static final int COMPLETION_BONUS_POINT = 1000;

    public static HomeResDTO.MissionPreview toMissionPreview(Mission mission) {
        int dDay = mission.getDeadline() != null
                ? (int) ChronoUnit.DAYS.between(LocalDate.now(), mission.getDeadline())
                : 0;

        return HomeResDTO.MissionPreview.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .category(mission.getStore().getRegion().getName())
                .condition(mission.getCondition())
                .rewardPoint(mission.getRewardPoint())
                .dDay(dDay)
                .status(MissionStatus.AVAILABLE.name())
                .build();
    }

    public static HomeResDTO.HomeInfo toHomeInfo(
            Page<Mission> missionPage,
            String location,
            Integer point,
            Integer completedCount
    ) {
        List<HomeResDTO.MissionPreview> previews = missionPage.getContent().stream()
                .map(HomeConverter::toMissionPreview)
                .collect(Collectors.toList());

        return HomeResDTO.HomeInfo.builder()
                .location(location)
                .point(point)
                .missionProgress(HomeResDTO.MissionProgress.builder()
                        .current(completedCount)
                        .total(MISSION_GOAL)
                        .bonusPoint(COMPLETION_BONUS_POINT)
                        .build())
                .missions(previews)
                .page(missionPage.getNumber() + 1)
                .totalPages(missionPage.getTotalPages())
                .build();
    }
}
