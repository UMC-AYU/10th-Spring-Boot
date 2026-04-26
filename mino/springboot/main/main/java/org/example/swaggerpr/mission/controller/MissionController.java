package org.example.swaggerpr.mission.controller;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.mission.dto.MissionReqDto;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.exception.code.MissionSuccessCode;
import org.example.swaggerpr.mission.service.MissionService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor //생성자 자동 생성
@RequestMapping("/users")
public class MissionController {
    private final MissionService missionService;

    // 미션 목록 조회
    @GetMapping("/{userid}/missions")
    public ApiResponse<MissionResDto.MissionListDto> UserMissionPreview(
            @PathVariable Long userid,
            @RequestParam(required = false) String status
    ) {
        // TODO : 추후 Service 연결
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.UserMissionPreview());
    }

    // 미션 성공 누르기
    @PatchMapping("/{userId}/missions/{missionId}")
    public ApiResponse<Void> CompleteMission(
            @RequestBody MissionReqDto.CompleteMissionDto dto
    ) {
        // TODO : 추후 Service 연결
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.CompleteMission(dto));
    }
}
