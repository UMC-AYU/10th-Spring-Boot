package com.aim.umc10th.domain.store.controller;

import com.aim.umc10th.domain.mission.converter.MissionConverter;
import com.aim.umc10th.domain.mission.dto.MissionResponseDTO;
import com.aim.umc10th.domain.mission.entity.Mission;
import com.aim.umc10th.domain.store.dto.StoreRequestDTO;
import com.aim.umc10th.domain.store.dto.StoreResponseDTO;
import com.aim.umc10th.domain.store.service.StoreQueryService;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Tag(name = "Store API", description = "가게 관련 API 입니다.")
public class StoreRestController {

    /*
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<StoreResponseDTO.CreateReviewResultDTO> createReview(
            @PathVariable(name = "storeId") Long storeId,
            @RequestBody StoreRequestDTO.ReviewDTO request
    ){
        //이번 미션에는 서버가 없으니 가짜 데이터를 보냈다.
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,  StoreResponseDTO.CreateReviewResultDTO.builder().reviewId(1L)
                .createdAt(LocalDateTime.now())
                .build());
    }
    */

    private final StoreQueryService storeQueryService;

    @GetMapping("/{regionName}/missions")
    @Operation(summary = "특정 지역의 도전 가능한 미션 목록 조회 API", description = "지역 이름을 통해 해당 지역의 미션 목록을 페이징하여 조회한다.")
    @Parameters({
            @Parameter(name="regionName", description = "지역의 이름 입니다."),
            @Parameter(name = "page", description = "페이지 번호이며, 0부터 시작입니다.")
    })

    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getMissionListByRegion(
            @PathVariable(name = "regionName") String regionName,
            @RequestParam(name = "page") Integer page) {

        //서비스 호출
        Page<Mission> missionList = storeQueryService.getMissionListByRegion(regionName, page);

        //컨버터를 이용해 DTO로 변환하여 응답
        return ApiResponse.onSuccess(MissionConverter.toMissionPreViewListDTO(missionList));
    }
}
