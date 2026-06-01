package com.example.springboot.domain.review.controller;

import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.exception.ReviewSuccessCode;
import com.example.springboot.domain.review.service.ReviewService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class ReviewController {

    private final ReviewService reviewService;

    // POST /stores/{storeId}/reviews
    @PostMapping("/{storeId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResDTO.CreateResult> createReview(
            @PathVariable Long storeId,
            @RequestParam Long memberId,
            @RequestBody @Valid ReviewReqDTO.Create dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_CREATE_OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(storeId, memberId, dto));
    }

    @GetMapping("/members/{memberId}/reviews")
    public ApiResponse<ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItem>> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "-1") String cursor,
            @RequestParam(defaultValue = "id") String query
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.REVIEW_CREATE_OK,
                reviewService.getMyReviews(memberId, pageSize, cursor, query)
        );
    }
}