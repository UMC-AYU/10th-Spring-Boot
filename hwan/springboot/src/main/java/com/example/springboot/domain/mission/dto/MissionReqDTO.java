package com.example.springboot.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;

public class MissionReqDTO {

    public record UpdateStatus(
            @NotBlank String status
    ) {}
}