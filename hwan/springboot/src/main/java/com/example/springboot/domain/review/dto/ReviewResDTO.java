package com.example.springboot.domain.review.dto;

import lombok.Builder;

public class ReviewResDTO {

    @Builder
    public record Create(
            Long reviewId,
            Long storeId,
            String storeName,
            Float score,
            String content,
            String createdAt
    ) {}
}