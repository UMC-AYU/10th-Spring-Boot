package org.example.swaggerpr.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDto {

    @Builder
    @Getter
    public static class CreateReviewResultDto {
        private Long reviewId;
        private Long missionId;
        private Integer score;
        private String content;
    }

    @Builder
    @Getter
    public static class MyReviewPreviewDto {
        private Long reviewId;
        private String storeName;
        private Integer score;
        private String content;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class MyReviewCursorListDto {
        private List<MyReviewPreviewDto> reviews;
        private Long nextCursorId;
        private Float nextCursorScore;
        private Integer size;
        private Boolean hasNext;
    }
}
