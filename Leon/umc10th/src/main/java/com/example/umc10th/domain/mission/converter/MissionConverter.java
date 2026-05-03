package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.stereotype.Component;

@Component
public class MissionConverter {

    public MissionResponseDTO.MissionInfo toMissionInfo(Mission mission) {
        return MissionResponseDTO.MissionInfo.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .build();
    }

    public MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.create(member, mission);
    }
}