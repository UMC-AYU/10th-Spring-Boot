package org.example.swaggerpr.mission.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.mission.dto.MissionReqDto;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.exception.code.MissionSuccessCode;
import org.example.swaggerpr.mission.service.MissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MissionController {
    private final MissionService missionService;

    @PostMapping("/missions/challenging")
    public ApiResponse<MissionResDto.MissionListDto> getMyChallengingMissions(
            @Valid @RequestBody MissionReqDto.ChallengingMissionSearchDto dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getChallengingMissions(dto));
    }

    @GetMapping("/{userid}/missions")
    public ApiResponse<MissionResDto.MissionListDto> getUserMissions(
            @PathVariable Long userid,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getUserMissions(userid, status, page, size));
    }

    @PatchMapping("/{userId}/missions/{missionId}")
    public ApiResponse<Void> completeMission(
            @PathVariable Long userId,
            @PathVariable Long missionId,
            @Valid @RequestBody MissionReqDto.CompleteMissionDto dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        missionService.completeMission(userId, missionId, dto);
        return ApiResponse.onSuccess(code, null);
    }
}
