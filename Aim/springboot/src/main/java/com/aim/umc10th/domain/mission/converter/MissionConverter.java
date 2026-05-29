package com.aim.umc10th.domain.mission.converter;

import com.aim.umc10th.domain.mission.dto.MissionRequestDTO;
import com.aim.umc10th.domain.mission.dto.MissionResponseDTO;
import com.aim.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // 단일 미션 엔터티를 PreviewDTO로 변환하기.
    public static MissionResponseDTO.MissionPreViewDTO toMissionPreViewDTO(Mission mission){
        return MissionResponseDTO.MissionPreViewDTO.builder()
                .storeName(mission.getStore() != null ? mission.getStore().getName() : "미등록 가게") //가게이름 가져오기
                .category((mission.getStore() != null && mission.getStore().getFoodCategory() != null)
                            ? mission.getStore().getFoodCategory().getName() : "미정")
                .missionPoint(mission.getReward() != null ? mission.getReward().longValue(): 0L)
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

    //클라이언트가 보낸 DTO를 DB에 저장할 Entity로 바꾸고, 저장된 Entity를 다시 ResponseDTO로 변환
    //DTO -> Entity
    public static Mission toMission(MissionRequestDTO.MissionCreateDTO request){
        return Mission.builder()
                .reward(request.getPoint())
                .missionSpec(request.getMissionSpec())
                .deadline(request.getDeadline())
                .build();
        }
    //Entity -> ResponseDTO
    public static MissionResponseDTO.CreateResultDTO toCreateResultDTO(Mission mission){
        return MissionResponseDTO.CreateResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
