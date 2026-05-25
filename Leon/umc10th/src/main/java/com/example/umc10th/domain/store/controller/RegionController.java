package com.example.umc10th.domain.store.controller;

import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.enums.MissionSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final MissionService missionService;

    @GetMapping("/{regionId}/missions")
    public ApiResponse<MissionResponseDTO.Pagination<MissionResponseDTO.MissionInfo>> getMissionsByRegion(
            @PathVariable Long regionId,
            @RequestParam(defaultValue = "LATEST") MissionSortType sort,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        BaseSuccessCode code = MissionSuccessCode.MISSION_OK;

        return ApiResponse.onSuccess(
                code,
                missionService.getMissionsByRegion(
                        regionId,
                        sort,
                        pageable
                )
        );
    }
}