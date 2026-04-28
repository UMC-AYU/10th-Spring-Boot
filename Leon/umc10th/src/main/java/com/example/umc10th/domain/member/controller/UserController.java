package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ApiResponse<MemberResponseDTO.GetMyInfo> getMyInfo() {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyInfo());
    }

    @GetMapping("/me/missions")
    public ApiResponse<List<MemberResponseDTO.MyMission>> getMyMissions(
            @RequestParam(required = false) String status
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyMissions(status));
    }

    @GetMapping("/me/reviews")
    public ApiResponse<List<MemberResponseDTO.MyReview>> getMyReviews() {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyReviews());
    }

    @PostMapping("/me/preferences")
    public ApiResponse<Void> setPreferences(
            @RequestBody MemberRequestDTO.SetPreferences dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        memberService.setPreferences(dto);
        return ApiResponse.onSuccess(code, null);
    }
}