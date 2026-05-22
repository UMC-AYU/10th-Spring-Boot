package org.example.swaggerpr.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;

public class ReviewReqDto {

    @Getter
    public static class CreateReviewDto {
        @NotNull(message = "score is required.")
        @Min(value = 1, message = "score must be greater than or equal to 1.")
        @Max(value = 5, message = "score must be less than or equal to 5.")
        private Integer score;

        @NotBlank(message = "content is required.")
        private String content;
    }

    @Getter
    public static class MyReviewCursorDto {
        @NotNull(message = "userId is required.")
        private Long userId;

        private Long cursorId;

        @Min(value = 1, message = "cursorScore must be greater than or equal to 1.")
        @Max(value = 5, message = "cursorScore must be less than or equal to 5.")
        private Float cursorScore;

        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;

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
