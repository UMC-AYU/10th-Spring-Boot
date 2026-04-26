package com.aim.umc10th.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {

    @Builder
    public record GetInfo( //(미션) 내 정보 조회 결과
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ){}

    //(미션) 미션 목록 조회용
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionListDTO{ //미션들이 담긴 바구니
        List<MissionDetailDTO> missionList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDetailDTO{ //바구니 안에 든 미션 하나하나
        Long missionId;
        Integer reward;
        String deadline;
        String missionSpec;
    }

    //회원가입 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class joinResultDTO{
        Long memberId;
        LocalDateTime createdAt;
    }

    //로그인 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{
        String accessToken;
        LocalDateTime issuedAt;
    }

    //내 포인트 조회 결과
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPointDTO{
        Integer point;
    }

    //내 리뷰 목록 조회 결과 (페이징도 포함)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewListDTO{
        List<MyReviewDetailDTO> reviewList;
        Integer totalPage; //전체 페이지 수
        Long totalElements; //전체 리뷰 개수
        Boolean isFirst; //첫 페이지 여부
        Boolean isLast; //마지막 페이지 여부
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewDetailDTO{
        Long reviewId;
        String storeName;
        Float score;
        String body;
        LocalDate createdAt;
    }




}
