package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public Review toEntity(Member member, Store store, ReviewRequestDTO.CreateReview dto) {
        return Review.create(
                member,
                store,
                dto.getRating(),
                dto.getContent()
        );
    }

    public ReviewResponseDTO.ReviewInfo toReviewInfo(Review review) {
        return ReviewResponseDTO.ReviewInfo.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating().intValue())
                .build();
    }
}