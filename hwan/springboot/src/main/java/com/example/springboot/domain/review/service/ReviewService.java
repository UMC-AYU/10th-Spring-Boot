package com.example.springboot.domain.review.service;

import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    // 다음 주차에 구현 예정 (Repository 연동)
    public ReviewResDTO.Create createReview(Long storeId, ReviewReqDTO.Create dto) {
        return null;
    }
}