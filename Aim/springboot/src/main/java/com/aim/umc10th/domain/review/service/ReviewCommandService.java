package com.aim.umc10th.domain.review.service;

import com.aim.umc10th.domain.review.entity.Review;


public interface ReviewCommandService {
    Review createReview(Long memberId, Long storeId, String body, Float score);
}
