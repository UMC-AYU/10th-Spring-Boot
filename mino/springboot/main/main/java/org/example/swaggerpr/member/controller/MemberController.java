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
@RequiredArgsConstructor //생성자 자동 생성

public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/users/signup")
    public ApiResponse<MemberResDto.SignupResultDto> signup(
            @RequestBody MemberReqDto.SignupDto dto
    ) {
        // TODO: 추후 Service 연결
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.Singup(dto));
    }

    @GetMapping("/users/{userid}/mypage")
    public ApiResponse<MemberResDto.MyPageDto> getMyPage(
            @PathVariable Long userid
    ) {
        // TODO: 추후 Service 연결
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyPage(userid));
    }

    @GetMapping("/users/home")
    public ApiResponse<Void> getHome() {
        // TODO: 추후 Service 연결
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getHome());
    }
}
