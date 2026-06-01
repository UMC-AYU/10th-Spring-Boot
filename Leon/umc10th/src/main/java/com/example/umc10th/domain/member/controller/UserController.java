package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.UserService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.enums.MissionSortType;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.global.enums.ReviewSortType;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<MemberResponseDTO.GetMyInfo> getMyInfo(
            @AuthenticationPrincipal AuthMember member
    ) {
        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;
        return ApiResponse.onSuccess(code, userService.getMyInfo(member.getMember()));
    }

    @PostMapping("/me/missions")
    public ApiResponse<MemberResponseDTO.Pagination<MemberResponseDTO.MyMission>> getMyMissions(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(required = false) MissionStatus status,
            @RequestParam(defaultValue = "LATEST") MissionSortType sort,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;

        return ApiResponse.onSuccess(code, userService.getMyMissions(
                member.getMember().getId(),
                status,
                sort,
                pageable
        ));
    }

    @GetMapping("/me/reviews")
    public ApiResponse<MemberResponseDTO.Pagination<MemberResponseDTO.MyReview>> getMyReviews(
            @AuthenticationPrincipal AuthMember member,
            @RequestParam(defaultValue = "LATEST") ReviewSortType sort,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;

        return ApiResponse.onSuccess(
                code,
                userService.getMyReviews(
                        member.getMember().getId(),
                        sort,
                        pageable
                )
        );
    }

    @PostMapping("/me/categories")
    public ApiResponse<Void> setCategories(
            @AuthenticationPrincipal AuthMember member,
            @RequestBody @Valid MemberRequestDTO.SetCategories dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.MEMBER_OK;
        userService.setCategories(member.getMember().getId(), dto.getCategoryIds());
        return ApiResponse.onSuccess(code, null);
    }
}