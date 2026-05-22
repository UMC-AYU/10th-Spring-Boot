package org.example.swaggerpr.mission.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MissionReqDto {

    @Getter
    public static class CompleteMissionDto {
        @NotBlank(message = "status is required.")
        private String status;
    }

    @Getter
    public static class OngoingMissionSearchDto {
        @NotNull(message = "userId is required.")
        private Long userId;

        @Min(value = 0, message = "page must be greater than or equal to 0.")
        private Integer page = 0;

        @Min(value = 1, message = "size must be greater than or equal to 1.")
        private Integer size = 10;
    }
}
