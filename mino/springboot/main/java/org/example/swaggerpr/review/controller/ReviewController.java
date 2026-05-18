package org.example.swaggerpr.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.exception.code.ReviewSuccessCode;
import org.example.swaggerpr.review.service.ReviewService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/missions/{missionid}/reviews")
    public ApiResponse<ReviewResDto.CreateReviewResultDto> createReview(
            @PathVariable Long missionid,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody ReviewReqDto.CreateReviewDto dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(userId, missionid, dto));
    }

    @PostMapping("/users/reviews")
    public ApiResponse<ReviewResDto.MyReviewCursorListDto> getMyReviews(
            @Valid @RequestBody ReviewReqDto.MyReviewCursorDto dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.getMyReviews(dto));
    }
}
