package com.example.springboot.domain.review.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResDTO {

    @Builder
    public record CreateResult(
            Long reviewId,
            Long storeId,
            String storeName,
            Float score,
            String content,
            String createdAt
    ) {}

    @Builder
    public record ReviewItem(
            Long reviewId,
            String content,
            Float score,
            String storeName,
            String createdAt
    ) {}

    @Builder
    public record CursorPagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {}
}