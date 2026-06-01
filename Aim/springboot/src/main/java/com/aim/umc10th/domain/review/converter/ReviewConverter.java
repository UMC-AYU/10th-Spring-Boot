package com.aim.umc10th.domain.review.converter;

import com.aim.umc10th.domain.review.dto.ReviewResponseDTO;
import com.aim.umc10th.domain.review.entity.Review;

public class ReviewConverter {

    public static ReviewResponseDTO.CreateResultDTO toCreateResultDTO(Review review) {
        return ReviewResponseDTO.CreateResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}