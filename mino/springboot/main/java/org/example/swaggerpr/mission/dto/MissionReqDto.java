package org.example.swaggerpr.mission.dto;

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
        @NotBlank(message = "status is required.")
        private String status;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChallengingMissionSearchDto {
        @NotNull(message = "userId is required.")
        private Long userId;

        @Min(value = 0, message = "page must be greater than or equal to 0.")
        private Integer page = 0;

        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;
    }
}
