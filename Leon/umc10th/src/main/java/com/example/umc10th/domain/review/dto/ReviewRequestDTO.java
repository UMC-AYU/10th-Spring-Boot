package com.example.umc10th.domain.review.dto;

import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class CreateReview {
        private Integer rating;
        private String content;
    }
}