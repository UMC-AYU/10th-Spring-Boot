package org.example.swaggerpr.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionReqDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CompleteMissionDto {
        @Schema(description = "Mission status to change", example = "COMPLETE")
        @NotBlank(message = "status is required.")
        private String status;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChallengingMissionSearchDto {
        public ChallengingMissionSearchDto(Long userId, Integer page, Integer size) {
            this.userId = userId;
            this.page = page;
            this.size = size;
        }

        @Schema(description = "Member ID", example = "1")
        @NotNull(message = "userId is required.")
        private Long userId;

        @Schema(description = "Page number", example = "0")
        @NotNull(message = "page is required.")
        @Min(value = 0, message = "page must be greater than or equal to 0.")
        private Integer page = 0;

        @Schema(description = "Page size", example = "10")
        @NotNull(message = "size is required.")
        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OngoingMissionSearchDto {
        @Schema(description = "Member ID", example = "1")
        @NotNull(message = "userId is required.")
        private Long userId;

        @Schema(description = "Page number", example = "0")
        @NotNull(message = "page is required.")
        @Min(value = 0, message = "page must be greater than or equal to 0.")
        private Integer page = 0;

        @Schema(description = "Page size", example = "10")
        @NotNull(message = "size is required.")
        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;
    }
}
