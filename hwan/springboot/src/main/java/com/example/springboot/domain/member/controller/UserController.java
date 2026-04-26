package com.example.springboot.domain.member.controller;

import com.example.springboot.domain.member.dto.UserReqDTO;
import com.example.springboot.domain.member.dto.UserResDTO;
import com.example.springboot.domain.member.exception.UserSuccessCode;
import com.example.springboot.domain.member.service.UserService;
import com.example.springboot.global.apiPayload.ApiResponse;
import com.example.springboot.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    // POST /auth/signup
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResDTO.SignUp> signUp(@RequestBody @Valid UserReqDTO.SignUp dto) {
        BaseSuccessCode code = UserSuccessCode.SIGNUP_SUCCESS;
        return ApiResponse.onSuccess(code, userService.signUp(dto));
    }
}