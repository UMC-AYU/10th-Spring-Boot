package com.example.springboot.domain.mission.converter;

import com.example.springboot.domain.mission.dto.MissionResDTO;
import com.example.springboot.domain.mission.entity.mapping.UserMission;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResDTO.MissionItem toMissionItem(UserMission um) {
        LocalDate deadline = um.getMission().getDeadline();
        int dDay = deadline != null
                ? (int) ChronoUnit.DAYS.between(LocalDate.now(), deadline)
                : 0;

        return MissionResDTO.MissionItem.builder()
                .userMissionId(um.getId())
                .storeName(um.getMission().getStore().getName())
                .condition(um.getMission().getCondition())
                .rewardPoint(um.getMission().getRewardPoint())
                .rewardPercent(0)
                .dDay(dDay)
                .status(um.getStatus().name())
                .hasReview(false)
                .build();
    }

    public static MissionResDTO.MissionList toMissionList(Page<UserMission> page, String status) {
        List<MissionResDTO.MissionItem> items = page.getContent().stream()
                .map(MissionConverter::toMissionItem)
                .collect(Collectors.toList());

        return MissionResDTO.MissionList.builder()
                .status(status)
                .missions(items)
                .page(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .build();
    }
}