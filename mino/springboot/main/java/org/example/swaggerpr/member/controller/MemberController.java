package org.example.swaggerpr.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.exception.code.MemberSuccessCode;
import org.example.swaggerpr.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/users/signup")
    public ApiResponse<MemberResDto.SignupResultDto> signup(
            @RequestBody MemberReqDto.SignupDto dto
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
    public ApiResponse<MemberResDto.HomeDto> getHome(
            @RequestParam(defaultValue = "1") Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // API 명세서에는 query parameter가 없지만, 화면의 "현재 선택된 지역" 미션 목록을 구현하려면 regionId가 필요하다.
        // 인증/지역 선택 상태 저장 기능이 아직 없으므로 query parameter로 지역을 전달받도록 설계했다.
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getHome(regionId, page, size));
    }
}
