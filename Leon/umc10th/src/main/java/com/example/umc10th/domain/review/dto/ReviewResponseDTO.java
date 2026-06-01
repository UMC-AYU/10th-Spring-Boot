package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

public class ReviewResponseDTO {

    @Getter
    @Builder
    public static class ReviewInfo {
        private Long reviewId;
        private BigDecimal rating;
        private String content;
    }
}