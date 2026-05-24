package org.example.swaggerpr.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.ApiResponse;
import org.example.swaggerpr.global.apiPayload.code.BaseSuccessCode;
import org.example.swaggerpr.global.apiPayload.code.GeneralErrorCode;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.global.security.AuthMember;
import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.exception.code.MemberSuccessCode;
import org.example.swaggerpr.member.service.MemberService;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.exception.code.MissionSuccessCode;
import org.example.swaggerpr.mission.service.MissionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "Member API")
@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MissionService missionService;

    @Operation(summary = "Sign up")
    @PostMapping("/auth/users/signup")
    public ApiResponse<MemberResDto.SignupResultDto> signup(
            @Valid @RequestBody MemberReqDto.SignupDto dto
    ) {
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.signup(dto));
    }

    @Operation(summary = "Get my page")
    @GetMapping("/users/{userId}/mypage")
    public ApiResponse<MemberResDto.MyPageDto> getMyPage(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        validateCurrentUser(userId, authMember);
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyPage(userId));
    }

    @Operation(summary = "Get home nearby missions")
    @GetMapping("/users/home")
    public ApiResponse<MissionResDto.NearbyMissionListDto> getNearbyMissions(
            @Parameter(description = "Region ID")
            @RequestParam(defaultValue = "1") @NotNull @Min(1) Long regionId,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") @Min(1) Integer size,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getNearbyMissions(authMember.getMember().getId(), regionId, page, size));
    }

    private void validateCurrentUser(Long userId, AuthMember authMember) {
        if (!userId.equals(authMember.getMember().getId())) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }
    }
}
