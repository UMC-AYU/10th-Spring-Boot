package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.AuthService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDTO.GetMyInfo> signup(
            @RequestBody @Valid MemberRequestDTO.Signup dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;
        return ApiResponse.onSuccess(code, authService.signup(dto));
    }

    @PostMapping("/login")
    public ApiResponse<MemberResponseDTO.Login> login(
            @RequestBody @Valid MemberRequestDTO.Login dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;
        return ApiResponse.onSuccess(code, authService.login(dto));
    }
}