package com.aim.umc10th.domain.member.controller;

import com.aim.umc10th.domain.member.dto.MemberRequestDTO;
import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthRestController {
    //회원가입 API
    @PostMapping("/signup")
    public ApiResponse<MemberResponseDTO.joinResultDTO>join(
            @RequestBody MemberRequestDTO.joinDTO request
    ){
        //가짜 응답 데이터
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberResponseDTO.joinResultDTO.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .build());
    }

    //로그인 API
    @PostMapping("/login")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(
            @RequestBody MemberRequestDTO.LoginDTO request
    ){
        //가짜 토큰 응답
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberResponseDTO.LoginResultDTO.builder()
                .accessToken("dummy-access-token-12345")
                .issuedAt(LocalDateTime.now())
                .build());
    }
}
