package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class ReviewResponseDTO {

    @Getter
    @Builder
    public static class ReviewInfo {
        private Long reviewId;
        private BigDecimal rating;
        private String content;
    }

    @Getter
    @Builder
    public static class Pagination<T> {
        private List<T> data;
        private Integer pageNumber;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
        private Boolean isLast;
    }
}