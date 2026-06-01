package org.example.swaggerpr.mission.converter;

import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

public class MissionConverter {
    public static MissionResDto.MissionListDto toMissionListDto(Page<MemberMission> memberMissions) {
        return MissionResDto.MissionListDto.builder()
                .missions(memberMissions.getContent().stream()
                        .map(memberMission -> MissionResDto.MissionPreviewDto.builder()
                                .missionId(memberMission.getMission().getId())
                                .storeName(memberMission.getMission().getStore().getName())
                                .content(memberMission.getMission().getContent())
                                .rewardPoint(memberMission.getMission().getRewardPoint())
                                .status(memberMission.getStatus().name())
                                .build())
                        .toList())
                .page(memberMissions.getNumber())
                .size(memberMissions.getSize())
                .totalElements(memberMissions.getTotalElements())
                .totalPages(memberMissions.getTotalPages())
                .build();
    }
}
