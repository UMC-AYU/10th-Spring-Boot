package com.aim.umc10th.domain.member.converter;

import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static MemberResponseDTO.GetInfo toGetInfo(
            Member member
    ){
        return MemberResponseDTO.GetInfo.builder()
                .email((member.getEmail()))
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    public static MemberResponseDTO.MissionListDTO toMissionListDTO(Page<MemberMission> missionList) {

        List<MemberResponseDTO.MissionDetailDTO> missionDetailDTOList = missionList.stream()
                .map(memberMission -> MemberResponseDTO.MissionDetailDTO.builder()
                        .missionId(memberMission.getMission().getId())
                        .storeName(memberMission.getMission().getStore().getName())
                        .reward(memberMission.getMission().getReward())
                        .missionSpec(memberMission.getMission().getMissionSpec())
                        .status(memberMission.getStatus().toString())
                        .createdAt(memberMission.getCreatedAt().toLocalDate())
                        .build()
                ).collect(Collectors.toList());

        return MemberResponseDTO.MissionListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionDetailDTOList.size())
                .missionList(missionDetailDTOList)
                .build();
    }

    public static MemberResponseDTO.MyPageResultDTO toMyPageResultDTO(Member member, Integer reviewCount){
        return MemberResponseDTO.MyPageResultDTO.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phone_number(member.getPhoneNumber())
                .point(member.getPoint())
                .reviewCount(reviewCount)
                .build();
    }
}
