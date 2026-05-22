package org.example.swaggerpr.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.global.apiPayload.code.GeneralErrorCode;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.global.security.AuthMember;
import org.example.swaggerpr.review.converter.ReviewConverter;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.exception.code.ReviewSuccessCode;
import org.example.swaggerpr.review.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "Review API")
@Validated
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Create review")
    @PostMapping("/missions/{missionId}/reviews")
    public ApiResponse<ReviewResDto.CreateReviewResultDto> createReview(
            @Parameter(description = "Mission ID")
            @PathVariable @NotNull @Min(1) Long missionId,
            @Valid @RequestBody ReviewReqDto.CreateReviewDto dto,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.createReview(authMember.getMember().getId(), missionId, dto));
    }

    @Operation(summary = "Get my reviews")
    @GetMapping("/users/{userId}/reviews")
    public ApiResponse<ReviewResDto.MyReviewCursorListDto> getMyReviews(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @Parameter(description = "Review ID cursor")
            @RequestParam(required = false) @Min(1) Long cursorId,
            @Parameter(description = "Review score cursor")
            @RequestParam(required = false) @Min(1) @Max(5) Integer cursorScore,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "ID") @NotNull ReviewReqDto.SortBy sortBy,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        validateCurrentUser(userId, authMember);
        BaseSuccessCode code = ReviewSuccessCode.OK;
        ReviewReqDto.MyReviewCursorDto dto =
                ReviewConverter.toMyReviewCursorDto(userId, cursorId, cursorScore, size, sortBy);
        return ApiResponse.onSuccess(code, reviewService.getMyReviews(dto));
    }

    @Operation(summary = "Get my reviews by request body")
    @PostMapping("/users/reviews")
    public ApiResponse<ReviewResDto.MyReviewCursorListDto> getMyReviews(
            @Valid @RequestBody ReviewReqDto.MyReviewCursorDto dto,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        validateCurrentUser(dto.getUserId(), authMember);
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.getMyReviews(dto));
    }

    private void validateCurrentUser(Long userId, AuthMember authMember) {
        if (!userId.equals(authMember.getMember().getId())) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }
    }
}
