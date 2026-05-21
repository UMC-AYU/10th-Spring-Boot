package com.example.springboot.domain.member.controller;

import com.example.springboot.domain.member.dto.HomeResDTO;
import com.example.springboot.domain.member.service.HomeService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import com.example.springboot.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ApiResponse<HomeResDTO.HomeInfo> getHome(
            @RequestParam Long memberId,
            @RequestParam Long regionId,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, homeService.getHomeInfo(memberId, regionId, page));
    }
}