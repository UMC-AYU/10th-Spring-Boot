package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.enums.MissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<MemberResponseDTO.GetMyInfo> getMyInfo() {
        Long memberId = 1L;
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.getMyInfo(memberId));
    }

    @GetMapping("/me/missions")
    public ApiResponse<Page<MemberResponseDTO.MyMission>> getMyMissions(
            @RequestParam(required = false) MissionStatus status,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Long memberId = 1L;
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.getMyMissions(memberId, status, pageable));
    }

    @GetMapping("/me/reviews")
    public ApiResponse<List<MemberResponseDTO.MyReview>> getMyReviews() {
        Long memberId = 1L;
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, userService.getMyReviews(memberId));
    }

    @PostMapping("/me/preferences")
    public ApiResponse<Void> setPreferences(
            @RequestBody MemberRequestDTO.SetPreferences dto
    ) {
        Long memberId = 1L;
        BaseSuccessCode code = MemberSuccessCode.OK;
        userService.setPreferences(memberId, dto.getCategoryIds());
        return ApiResponse.onSuccess(code, null);
    }
}