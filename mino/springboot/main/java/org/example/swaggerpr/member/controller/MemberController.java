package org.example.swaggerpr.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.exception.code.MemberSuccessCode;
import org.example.swaggerpr.member.service.MemberService;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.exception.code.MissionSuccessCode;
import org.example.swaggerpr.mission.service.MissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MissionService missionService;

    @PostMapping("/auth/users/signup")
    public ApiResponse<MemberResDto.SignupResultDto> signup(
            @Valid @RequestBody MemberReqDto.SignupDto dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.signup(dto));
    }

    @GetMapping("/users/{userid}/mypage")
    public ApiResponse<MemberResDto.MyPageDto> getMyPage(
            @PathVariable Long userid
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyPage(userid));
    }

    @GetMapping("/users/home")
    public ApiResponse<MissionResDto.NearbyMissionListDto> getNearbyMissions(
            @RequestParam(defaultValue = "1") Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getNearbyMissions(regionId, page, size));
    }
}
