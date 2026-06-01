package com.aim.umc10th.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO{
        private Long reviewId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewListDTO{
        List<MyReviewDTO> reviewList;
        Integer listSize;
        Long nextCursor; // 다음 조회를 위한 커서 (Id 순일 땐 lastId, 별점 순일 땐 lastScore)
        Boolean hasNext; //다음 페이지가 존재하는지 여부 확인
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewDTO{
        Long reviewId;
        String storeName;
        Float score;
        String body;
        LocalDate createdAt;
    }

}
