package com.aim.umc10th.domain.member.controller;

import com.aim.umc10th.domain.member.dto.MemberRequestDTO;
import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.service.MemberService;
import com.aim.umc10th.global.apiPayload.ApiResponse;
import com.aim.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
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

    private final MemberService memberService;

    //회원가입 API
    @PostMapping("/signup")
    public ApiResponse<String>join(
            @RequestBody @Valid MemberRequestDTO.joinDTO request
    ){
        String result = memberService.createUser(request);

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    //로그인 API
    @PostMapping("/login")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(
            @RequestBody MemberRequestDTO.LoginDTO request
    ){
        //그 전에는 가짜 토큰이 나오도록 했는데, MemberService에 만든 진짜 로그인 로직을 호출한다.
        String realToken = memberService.login(request.getEmail(), request.getPassword());

        //서비스단에서 발행된 진짜 암호화 JWT 토큰을 응답에 실어준다.
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberResponseDTO.LoginResultDTO.builder()
                .accessToken(realToken)
                .issuedAt(LocalDateTime.now())
                .build()
        );
    }
}
