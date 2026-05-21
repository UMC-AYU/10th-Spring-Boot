package com.aim.umc10th.domain.mission.converter;

import com.aim.umc10th.domain.mission.dto.MissionResponseDTO;
import com.aim.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // 단일 미션 엔터티를 PreviewDTO로 변환하기.
    public static MissionResponseDTO.MissionPreViewDTO toMissionPreViewDTO(Mission mission){
        return MissionResponseDTO.MissionPreViewDTO.builder()
                .storeName(mission.getStore().getName()) //가게이름 가져오기
                .category(mission.getStore().getFoodCategory().getName())
                .missionPoint(mission.getReward().longValue())
                .content(mission.getMissionSpec())
                .build();
    }

    //페이징된 미션 목록을 PreViewListDTO로 변환
    public static MissionResponseDTO.MissionPreViewListDTO toMissionPreViewListDTO(Page<Mission> missionList){

        //리스트 내부의 각 엔터티를 DTO로 변환
        List<MissionResponseDTO.MissionPreViewDTO> missionPreViewDTOList = missionList.getContent().stream()
                .map(MissionConverter::toMissionPreViewDTO)
                .collect(Collectors.toList());

        return MissionResponseDTO.MissionPreViewListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionPreViewDTOList.size())
                .missionList(missionPreViewDTOList)
                .build();
    }
}
