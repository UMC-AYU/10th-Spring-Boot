package com.example.umc10th.domain.review.dto;

import lombok.Getter;

import java.math.BigDecimal;

public class ReviewRequestDTO {

    @Getter
    public static class CreateReview {
        private BigDecimal rating;
        private String content;
    }
}