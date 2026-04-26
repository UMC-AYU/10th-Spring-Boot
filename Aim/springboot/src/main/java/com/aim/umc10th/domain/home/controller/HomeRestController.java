package com.aim.umc10th.domain.home.controller;

import com.aim.umc10th.domain.home.dto.HomeResponseDTO;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions") //URI 시작점
public class HomeRestController {

    @GetMapping("/{regionId}/missions")
    public ApiResponse<HomeResponseDTO.RegionMissionListDTO> getRegionMissions(
            @PathVariable(name = "regionId") Long regionId
    ){
        //가짜 데이터 생성
        HomeResponseDTO.MissionSummaryDTO mission = HomeResponseDTO.MissionSummaryDTO.builder()
                .missionId(1L)
                .storeNAme("마라탕탕")
                .reward(500)
                .missionSpec("리뷰작성")
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, HomeResponseDTO.RegionMissionListDTO.builder()
                .missionList(List.of(mission))
                .build());
    }
}
