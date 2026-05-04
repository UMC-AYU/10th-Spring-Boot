package org.example.swaggerpr.review.dto;

import lombok.Getter;

public class ReviewReqDto {

    @Getter
    public static class CreateReviewDto {
        private Integer score;
        private String content;
    }
}
