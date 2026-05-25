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

    @Operation(summary = "Login")
    @PostMapping("/auth/users/login")
    public ApiResponse<MemberResDto.LoginResultDto> login(
            @Valid @RequestBody MemberReqDto.LoginDto dto
    ) {
        // 로그인 성공 시 서비스에서 JWT access token을 발급해 응답 DTO에 담아준다.
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.login(dto));
    }

    @Operation(summary = "Get my page with JWT")
    @GetMapping("/users/mypage")
    public ApiResponse<MemberResDto.MyPageDto> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        // PathVariable의 userId 대신 JWT 인증 객체의 회원 정보를 기준으로 마이페이지를 조회한다.
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getMyPage(authMember.getMember()));
    }

    @Operation(summary = "Get my page")
    @GetMapping("/users/{userId}/mypage")
    public ApiResponse<MemberResDto.MyPageDto> getMyPage(
            @Parameter(description = "Member ID")
            @PathVariable @NotNull @Min(1) Long userId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        // 기존 URL 호환성을 유지하되, JWT 사용자와 path의 userId가 다르면 접근을 막는다.
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
        // 다른 회원 ID로 기존 마이페이지 API를 호출하는 것을 방지한다.
        if (!userId.equals(authMember.getMember().getId())) {
            throw new ProjectException(GeneralErrorCode.FORBIDDEN);
        }
    }
}
