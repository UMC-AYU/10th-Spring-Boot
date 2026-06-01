package com.aim.umc10th.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

public class MissionRequestDTO {

    @Getter
    public static class MissionCreateDTO{
        @NotNull
        private Long point; // 포인트

        @NotBlank
        private String missionSpec; //미션 내용

        @NotNull
        private LocalDateTime deadline; //마감 기한
    }
}
