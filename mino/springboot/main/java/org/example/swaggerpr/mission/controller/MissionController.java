package org.example.swaggerpr.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.global.apiPayload.code.GeneralErrorCode;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.global.security.CustomUserDetails;
import org.example.swaggerpr.mission.converter.MissionConverter;
import org.example.swaggerpr.mission.dto.MissionReqDto;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.exception.code.MissionSuccessCode;
import org.example.swaggerpr.mission.service.MissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mission", description = "Mission API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MissionController {
    private final MissionService missionService;

    @Operation(summary = "Get challenging missions")
    @GetMapping("/{userId}/missions/challenging")
    public ApiResponse<MissionResDto.MissionListDto> getMyChallengingMissions(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        validateCurrentUser(userId, userDetails);
        BaseSuccessCode code = MissionSuccessCode.OK;
        MissionReqDto.ChallengingMissionSearchDto dto =
                MissionConverter.toChallengingMissionSearchDto(userId, page, size);
        return ApiResponse.onSuccess(code, missionService.getChallengingMissions(dto));
    }

    @Operation(summary = "Get member missions")
    @GetMapping("/{userId}/missions")
    public ApiResponse<MissionResDto.MissionListDto> getUserMissions(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @Parameter(description = "Mission status")
            @RequestParam(required = false) String status,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        validateCurrentUser(userId, userDetails);
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getUserMissions(userId, status, page, size));
    }

    @Operation(summary = "Complete mission")
    @PatchMapping("/{userId}/missions/{missionId}")
    public ApiResponse<Void> completeMission(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @Parameter(description = "Mission ID")
            @PathVariable @NotNull @Min(1) Long missionId,
            @Valid @RequestBody MissionReqDto.CompleteMissionDto dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        validateCurrentUser(userId, userDetails);
        BaseSuccessCode code = MissionSuccessCode.OK;
        missionService.completeMission(userId, missionId, dto);
        return ApiResponse.onSuccess(code, null);
    }

    private void validateCurrentUser(Long userId, CustomUserDetails userDetails) {
        if (!userId.equals(userDetails.getMemberId())) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }
    }
}
