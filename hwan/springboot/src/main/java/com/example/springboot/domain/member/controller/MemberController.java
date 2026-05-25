package com.example.springboot.domain.member.controller;

import com.example.springboot.domain.member.dto.MemberReqDTO;
import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.exception.MemberSuccessCode;
import com.example.springboot.domain.member.service.MemberService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.global.security.entity.AuthMember;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "회원", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberResDTO.SignUp>> signUp(@RequestBody @Valid MemberReqDTO.SignUp dto) {
        BaseSuccessCode code = MemberSuccessCode.SIGNUP_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onSuccess(code, memberService.signUp(dto)));
    }

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/mypage")   // /{memberId}/mypage → /mypage 로 변경
    public ResponseEntity<ApiResponse<MemberResDTO.MyPage>> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        BaseSuccessCode code = MemberSuccessCode.MYPAGE_OK;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, memberService.getMyPage(authMember.getMember().getId())));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResDTO.Login>> login(
            @RequestBody @Valid MemberReqDTO.Login dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.LOGIN_SUCCESS;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onSuccess(code, memberService.login(dto)));
    }
}
