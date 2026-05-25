package com.example.springboot.domain.mission.controller;

import com.example.springboot.domain.mission.dto.MissionReqDTO;
import com.example.springboot.domain.mission.dto.MissionResDTO;
import com.example.springboot.domain.mission.exception.MissionSuccessCode;
import com.example.springboot.domain.mission.service.MissionService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ApiResponse<MissionResDTO.MissionList> getMissions(
            @RequestParam Long memberId,
            @RequestParam String status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_LIST_OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(memberId, status, pageable));
    }

    // PATCH /users/missions/{userMissionId}
    @PatchMapping("/{userMissionId}")
    public ApiResponse<MissionResDTO.MissionComplete> completeMission(
            @PathVariable Long userMissionId,
            @RequestBody @Valid MissionReqDTO.UpdateStatus dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_COMPLETE_OK;
        return ApiResponse.onSuccess(code, missionService.completeMission(userMissionId, dto));
    }
}