package com.example.springboot.domain.member.controller;

import com.example.springboot.domain.member.dto.MemberReqDTO;
import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.exception.MemberSuccessCode;
import com.example.springboot.domain.member.service.MemberService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;

    // POST /auth/signup
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberResDTO.SignUp>> signUp(@RequestBody @Valid MemberReqDTO.SignUp dto) {
        BaseSuccessCode code = MemberSuccessCode.SIGNUP_SUCCESS;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onSuccess(code, memberService.signUp(dto)));
    }

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MemberResDTO.MyPage>> getMyPage(
            @RequestParam Long memberId
    ) {
        BaseSuccessCode code = MemberSuccessCode.MYPAGE_OK;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onSuccess(code, memberService.getMyPage(memberId)));
    }
}
