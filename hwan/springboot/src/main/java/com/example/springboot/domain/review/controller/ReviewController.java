package com.example.springboot.domain.review.controller;

import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.exception.ReviewSuccessCode;
import com.example.springboot.domain.review.service.ReviewService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import com.example.springboot.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "리뷰", description = "리뷰 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성")
    @PostMapping("/{storeId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResDTO.CreateResult> createReview(
            @Parameter(description = "가게 ID") @PathVariable Long storeId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestBody @Valid ReviewReqDTO.Create dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_CREATE_OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(storeId, authMember.getMember().getId(), dto));
    }

    @Operation(summary = "내 리뷰 목록 조회")
    @GetMapping("/members/reviews")
    public ApiResponse<ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItem>> getMyReviews(
            @AuthenticationPrincipal AuthMember authMember,
            @Parameter(description = "페이지 크기 (최소 1)") @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "-1") String cursor,
            @Parameter(description = "정렬 기준 (id 또는 rating)") @RequestParam(defaultValue = "id") String query
    ) {
        return ApiResponse.onSuccess(
                ReviewSuccessCode.REVIEW_LIST_OK,
                reviewService.getMyReviews(authMember.getMember().getId(), pageSize, cursor, query)
        );
    }
}
