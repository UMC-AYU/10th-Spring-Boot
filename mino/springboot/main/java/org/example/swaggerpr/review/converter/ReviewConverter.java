package org.example.swaggerpr.review.converter;

import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.entity.Review;

public class ReviewConverter {
    public static ReviewResDto.CreateReviewResultDto toCreateReviewResultDto(Review review, Long missionId) {
        return ReviewResDto.CreateReviewResultDto.builder()
                .reviewId(review.getId())
                // Review는 ERD상 Store와 직접 연결되고 Mission과는 직접 연결되지 않는다.
                // 따라서 응답의 missionId는 API path variable 값을 그대로 사용한다.
                .missionId(missionId)
                .score(review.getScore().intValue())
                .content(review.getContent())
                .build();
    }
}
