package com.example.springboot.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReviewReqDTO {

    public record Create(
            @NotNull @DecimalMin("0.5") @DecimalMax("5.0") Float score,
            @NotBlank String content,
            List<String> images
    ) {}
}