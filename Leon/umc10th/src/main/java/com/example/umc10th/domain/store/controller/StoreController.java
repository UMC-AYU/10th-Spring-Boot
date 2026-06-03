package com.example.umc10th.domain.store.controller;

import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.enums.ReviewSortType;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final ReviewService reviewService;

    @GetMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.Pagination<ReviewResponseDTO.ReviewInfo>> getReviews(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "LATEST") ReviewSortType sort,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        BaseSuccessCode code = ReviewSuccessCode.REVIEW_OK;

        return ApiResponse.onSuccess(
                code,
                reviewService.getReviews(
                        storeId,
                        sort,
                        pageable
                )
        );
    }

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.ReviewInfo> createReview(
            @AuthenticationPrincipal AuthMember member,
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequestDTO.CreateReview dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.REVIEW_OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(
                member.getMember().getId(),
                storeId,
                dto
        ));
    }
}