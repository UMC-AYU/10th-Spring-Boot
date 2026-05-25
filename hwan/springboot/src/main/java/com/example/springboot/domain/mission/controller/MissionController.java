package com.example.springboot.domain.mission.controller;

import com.example.springboot.domain.mission.dto.MissionReqDTO;
import com.example.springboot.domain.mission.dto.MissionResDTO;
import com.example.springboot.domain.mission.exception.MissionSuccessCode;
import com.example.springboot.domain.mission.service.MissionService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import com.example.springboot.global.security.entity.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "미션", description = "미션 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "내 미션 목록 조회")
    @GetMapping("/missions")
    public ResponseEntity<ApiResponse<MissionResDTO.MissionList>> getMissions(
            @AuthenticationPrincipal AuthMember authMember,
            @Parameter(description = "미션 상태 (IN_PROGRESS, COMPLETE)") @RequestParam String status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_LIST_OK;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, missionService.getMissions(authMember.getMember().getId(), status, pageable)));
    }

    @Operation(summary = "미션 완료 처리")
    @PatchMapping("/missions/{userMissionId}")
    public ResponseEntity<ApiResponse<MissionResDTO.MissionComplete>> completeMission(
            @Parameter(description = "유저 미션 ID") @PathVariable Long userMissionId,
            @RequestBody @Valid MissionReqDTO.UpdateStatus dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.MISSION_COMPLETE_OK;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, missionService.completeMission(userMissionId, dto)));
    }
}
