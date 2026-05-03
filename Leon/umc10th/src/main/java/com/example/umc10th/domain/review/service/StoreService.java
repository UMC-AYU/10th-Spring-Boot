package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;

public interface StoreService {

    ReviewResponseDTO.ReviewInfo createReview(Long memberId, Long storeId, ReviewRequestDTO.CreateReview dto);
}