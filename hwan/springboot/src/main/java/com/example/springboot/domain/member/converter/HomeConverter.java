package com.example.springboot.domain.member.converter;

import com.example.springboot.domain.member.dto.HomeResDTO;
import com.example.springboot.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class HomeConverter {

    public static HomeResDTO.MissionPreview toMissionPreview(Mission mission) {
        int dDay = mission.getDeadline() != null
                ? (int) ChronoUnit.DAYS.between(LocalDate.now(), mission.getDeadline())
                : 0;

        return HomeResDTO.MissionPreview.builder()
                .userMissionId(mission.getId())
                .storeName(mission.getStore().getName())
                .category(mission.getStore().getRegion().getName())
                .condition(mission.getCondition())
                .rewardPoint(mission.getRewardPoint())
                .dDay(dDay)
                .status("AVAILABLE")
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
                        .total(10)
                        .bonusPoint(1000)
                        .build())
                .missions(previews)
                .page(missionPage.getNumber() + 1)
                .totalPages(missionPage.getTotalPages())
                .build();
    }
}