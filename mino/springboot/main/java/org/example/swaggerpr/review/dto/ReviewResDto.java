package org.example.swaggerpr.review.dto;

import lombok.Builder;
import lombok.Getter;

public class ReviewResDto {

    @Builder
    @Getter
    public static class CreateReviewResultDto {
        private Long reviewId;
        private Long missionId;
        private Integer score;
        private String content;
    }
}
