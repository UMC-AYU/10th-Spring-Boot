package org.example.swaggerpr.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewReqDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateReviewDto {
        @Schema(description = "리뷰 점수", example = "5")
        @NotNull(message = "score is required.")
        @Min(value = 1, message = "score must be greater than or equal to 1.")
        @Max(value = 5, message = "score must be less than or equal to 5.")
        private Integer score;

        @Schema(description = "리뷰 내용", example = "맛있고 친절했어요.")
        @NotBlank(message = "content is required.")
        private String content;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MyReviewCursorDto {
        public MyReviewCursorDto(Long userId, Long cursorId, Integer cursorScore, Integer size, SortBy sortBy) {
            this.userId = userId;
            this.cursorId = cursorId;
            this.cursorScore = cursorScore;
            this.size = size;
            this.sortBy = sortBy;
        }

        @Schema(description = "회원 ID", example = "1")
        @NotNull(message = "userId is required.")
        private Long userId;

        @Schema(description = "다음 페이지 조회 기준 리뷰 ID", example = "10")
        private Long cursorId;

        @Schema(description = "점수 정렬 시 다음 페이지 조회 기준 점수", example = "4")
        @Min(value = 1, message = "cursorScore must be greater than or equal to 1.")
        @Max(value = 5, message = "cursorScore must be less than or equal to 5.")
        private Integer cursorScore;

        @Schema(description = "페이지 크기", example = "10")
        @NotNull(message = "size is required.")
        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;

        @Schema(description = "리뷰 정렬 기준", example = "ID")
        @NotNull(message = "sortBy is required.")
        private SortBy sortBy = SortBy.ID;

        @AssertTrue(message = "cursorId is required when cursorScore is provided.")
        public boolean isValidScoreCursor() {
            return cursorScore == null || cursorId != null;
        }
    }

    public enum SortBy {
        ID, SCORE
    }
}
