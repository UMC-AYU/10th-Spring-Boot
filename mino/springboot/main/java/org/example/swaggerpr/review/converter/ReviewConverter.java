package org.example.swaggerpr.review.converter;

import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.entity.Review;

import java.time.LocalDateTime;

public class ReviewConverter {
    public static Review toReview(Member member, Mission mission, ReviewReqDto.CreateReviewDto dto) {
        return Review.builder()
                .member(member)
                .store(mission.getStore())
                .score(dto.getScore())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ReviewResDto.CreateReviewResultDto toCreateReviewResultDto(Review review, Long missionId) {
        return ReviewResDto.CreateReviewResultDto.builder()
                .reviewId(review.getId())
                .missionId(missionId)
                .score(review.getScore())
                .content(review.getContent())
                .build();
    }

    public static ReviewResDto.MyReviewPreviewDto toMyReviewPreviewDto(Review review) {
        return ReviewResDto.MyReviewPreviewDto.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
