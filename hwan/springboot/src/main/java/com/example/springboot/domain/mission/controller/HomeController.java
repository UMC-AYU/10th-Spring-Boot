package com.example.springboot.domain.mission.controller;

import com.example.springboot.domain.mission.dto.HomeResDTO;
import com.example.springboot.domain.mission.service.HomeService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import com.example.springboot.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "홈", description = "홈 화면 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "홈 화면 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<HomeResDTO.HomeInfo>> getHome(
            @Parameter(description = "회원 ID") @PathVariable Long memberId,
            @Parameter(description = "지역 ID") @RequestParam Long regionId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, homeService.getHomeInfo(memberId, regionId, pageable)));
    }
}
