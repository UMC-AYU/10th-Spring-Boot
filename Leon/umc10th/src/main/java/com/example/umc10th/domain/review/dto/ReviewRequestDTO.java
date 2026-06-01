package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

public class ReviewRequestDTO {

    @Getter
    public static class CreateReview {

        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "5.0")
        private BigDecimal rating;

        @NotBlank
        private String content;
    }
}