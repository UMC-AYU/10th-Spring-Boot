package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

public class ReviewResponseDTO {

    @Getter
    @Builder
    public static class ReviewInfo {
        private Long reviewId;
        private Integer rating;
        private String content;
    }
}