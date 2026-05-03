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
@RequiredArgsConstructor
@RequestMapping("/users")
public class MissionController {
    private final MissionService missionService;

    @GetMapping("/{userid}/missions")
    public ApiResponse<MissionResDto.MissionListDto> userMissionPreview(
            @PathVariable Long userid,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // 화면의 진행중/진행완료 목록은 N+1문제가 발생할 수 있어 @Query + Pageable 기반으로 조회한다.
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getUserMissions(userid, status, page, size));
    }

    @PatchMapping("/{userId}/missions/{missionId}")
    public ApiResponse<Void> completeMission(
            @PathVariable Long userId,
            @PathVariable Long missionId,
            @RequestBody MissionReqDto.CompleteMissionDto dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.completeMission(userId, missionId, dto));
    }
}
