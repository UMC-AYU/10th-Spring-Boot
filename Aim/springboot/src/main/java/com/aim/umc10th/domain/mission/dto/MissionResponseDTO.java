package com.aim.umc10th.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MissionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreViewDTO{
        String storeName; //가게이름
        String category; //음식 카테고리
        Long missionPoint; //미션 포인트
        String content; //미션 내용
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreViewListDTO{
        List<MissionPreViewDTO> missionList;
        Integer listSize; //이번 페이지에 담긴 개수
        Integer totalPage; // 전체 페이지 수
        Long totalElements; // 전체 데이터 개수
        Boolean isFirst; //첫 페이지 여부
        Boolean isLast; // 마지막 페이지 여부
    }
}
