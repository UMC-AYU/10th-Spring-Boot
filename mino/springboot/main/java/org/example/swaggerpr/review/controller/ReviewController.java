package org.example.swaggerpr.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.exception.code.ReviewSuccessCode;
import org.example.swaggerpr.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/missions/{missionid}/reviews")
    public ApiResponse<ReviewResDto.CreateReviewResultDto> createReview(
            @PathVariable Long missionid,
            @RequestParam Long userId,
            @RequestBody ReviewReqDto.CreateReviewDto dto
    ) {
        // 명세서는 Authorization Header에서 회원을 식별하지만 현재 프로젝트에는 인증 모듈이 없기 때문에 userId query parameter로 회원을 임시 식별한다.
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(userId, missionid, dto));
    }
}
