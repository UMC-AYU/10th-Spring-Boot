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
            Long point
    ){}

    //(미션) 미션 목록 조회 결과 (페이징 추가)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionListDTO{ //미션들이 담긴 바구니
        List<MissionDetailDTO> missionList;
        Integer listSize; //현재 페이지 데이터 개수
        Integer totalPage; //전체 페이지 수
        Long totalElements; //전제 데이터 개수
        Boolean isFirst; //첫 페이지 여부
        Boolean isLast;  //마지막 페이지 여부
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDetailDTO{ //바구니 안에 든 미션 하나하나
        Long missionId;
        String storeName; //가게 이름
        Long reward;
        String deadline;
        String missionSpec;
        String status; //CHALLENGING 또는 COMPLETE 구분
        LocalDate createdAt; //생성일자
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
        Long point;
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

        //미션2 커서용 필드
        Integer listSize; // 현재 리스트에 담긴 개수
        Long nextCursor; //다음 조회를 위한 커서 ID
        Boolean hasNext; // 다음 페이지 존재 여부 (Slice 사용 시 필요하다.)
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageResultDTO{
        String name;
        String email;
        String phone_number;
        Long point;
        Integer reviewCount;
    }

    // 전체 리스트 응답용 (페이징 정보 포함)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyMissionListDTO{
        List<MyMissionDTO> missionList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    //개별 미션 정보 응답용
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyMissionDTO{
        Long memberMissionId; //맴버-미션 연관관계 테이블의 ID
        String storeName; //어느 가게 미션인지
        String missionSpec; //미션 내용
        Long reward; //포인트
        String status; // 현재 상태 (CHALLENGING)
    }
}
