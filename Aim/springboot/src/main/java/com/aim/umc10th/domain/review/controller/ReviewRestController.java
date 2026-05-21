package com.aim.umc10th.domain.review.controller;

import com.aim.umc10th.domain.review.converter.ReviewConverter;
import com.aim.umc10th.domain.review.dto.ReviewRequestDTO;
import com.aim.umc10th.domain.review.dto.ReviewResponseDTO;
import com.aim.umc10th.domain.review.entity.Review;
import com.aim.umc10th.domain.review.service.ReviewCommandService;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")

public class ReviewRestController {
    private final ReviewCommandService reviewCommandService;

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.CreateResultDTO> createReview(
            @PathVariable Long storeId,
            @RequestBody ReviewRequestDTO.CreateDTO request){

        // 현재 로그인한 사용자 ID를 가져오는 로직
        Review review = reviewCommandService.createReview(1L, storeId, request.getBody(), request.getScore());
        return ApiResponse.onSuccess(ReviewConverter.toCreateResultDTO(review));

    }

}
