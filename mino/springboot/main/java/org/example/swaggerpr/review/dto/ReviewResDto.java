package org.example.swaggerpr.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDto {

    @Builder
    @Getter
    public static class CreateReviewResultDto {
        private final Long reviewId;
        private final Long missionId;
        private final Integer score;
        private final String content;
    }

    @Builder
    @Getter
    public static class MyReviewPreviewDto {
        private final Long reviewId;
        private final String storeName;
        private final Integer score;
        private final String content;
        private final LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class MyReviewCursorListDto {
        private final List<MyReviewPreviewDto> reviews;
        private final Long nextCursorId;
        private final Integer nextCursorScore;
        private final Integer size;
        private final Boolean hasNext;
    }
}
