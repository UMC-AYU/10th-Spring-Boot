package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MissionConverter {

    public MissionResponseDTO.MissionInfo toMissionInfo(Mission mission) {
        return MissionResponseDTO.MissionInfo.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .store(mission.getStore().getName())
                .build();
    }

    public MemberMission toMemberMission(Member member, Mission mission) {
        return MemberMission.create(member, mission);
    }

    public <T> MissionResponseDTO.Pagination<T> toPagination(Page<T> page) {

        return MissionResponseDTO.Pagination.<T>builder()
                .data(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .build();
    }
}