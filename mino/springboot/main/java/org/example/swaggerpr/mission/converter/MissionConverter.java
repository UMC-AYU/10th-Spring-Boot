package org.example.swaggerpr.mission.converter;

import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.entity.Mission;
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

    public static MissionResDto.NearbyMissionListDto toNearbyMissionListDto(Page<Mission> missions) {
        return MissionResDto.NearbyMissionListDto.builder()
                .regionName(missions.hasContent() ? missions.getContent().getFirst().getStore().getRegion().getName() : null)
                .missions(missions.getContent().stream()
                        .map(mission -> MissionResDto.NearbyMissionDto.builder()
                                .missionId(mission.getId())
                                .storeName(mission.getStore().getName())
                                .content(mission.getContent())
                                .rewardPoint(mission.getRewardPoint())
                                .build())
                        .toList())
                .page(missions.getNumber())
                .size(missions.getSize())
                .totalElements(missions.getTotalElements())
                .totalPages(missions.getTotalPages())
                .build();
    }
}
