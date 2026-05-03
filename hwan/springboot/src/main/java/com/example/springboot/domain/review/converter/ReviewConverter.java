package com.example.springboot.domain.review.converter;

import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.entity.Review;

import java.time.format.DateTimeFormatter;

public class ReviewConverter {

    public static ReviewResDTO.CreateResult toCreateDTO(Review review) {
        return ReviewResDTO.CreateResult.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}