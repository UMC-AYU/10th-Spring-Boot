package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ApiResponse<List<MissionResponseDTO.MissionInfo>> getMissions(
            @RequestParam(required = false) String status
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(status));
    }

    @PostMapping("/{missionId}/start")
    public ApiResponse<Void> startMission(
            @PathVariable Long missionId
    ) {
        Long memberId = 1L;
        BaseSuccessCode code = MissionSuccessCode.OK;
        missionService.startMission(memberId, missionId);
        return ApiResponse.onSuccess(code, null);
    }

    @PostMapping("/{missionId}/complete")
    public ApiResponse<Void> completeMission(
            @PathVariable Long missionId
    ) {
        Long memberId = 1L;
        BaseSuccessCode code = MissionSuccessCode.OK;
        missionService.completeMission(memberId, missionId);
        return ApiResponse.onSuccess(code, null);
    }
}