package org.example.swaggerpr.review.controller;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.exception.code.ReviewSuccessCode;
import org.example.swaggerpr.review.service.ReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //Restful api 사용
@RequiredArgsConstructor //생성자 자동 생성
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/missions/{missionid}/reviews")
    public ApiResponse<ReviewResDto.CreateReviewResultDto> CreateReview(
            @RequestBody ReviewReqDto.CreateReviewDto dto
    ){
        // TODO : 추후 Service 연결
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.CreateReview(dto));
    }
}
