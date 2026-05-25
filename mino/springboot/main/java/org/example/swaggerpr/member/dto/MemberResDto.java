package org.example.swaggerpr.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResDto {
    @Getter
    @Builder
    public static class SignupResultDto {
        private final Long userId;
        private final String email;
        private final String name;
    }

    @Getter
    @Builder
    // 로그인 성공 시 클라이언트가 Swagger Authorize에 넣을 JWT 정보를 반환한다.
    public static class LoginResultDto {
        private final Long userId;
        private final String email;
        private final String name;
        private final String tokenType;
        private final String accessToken;
    }

    @Getter
    @Builder
    // JWT 인증 객체 또는 기존 userId 조회 결과를 공통 마이페이지 응답 형태로 내려준다.
    public static class MyPageDto {
        private final Long userId;
        private final String name;
        private final String email;
        private final String phone;
        private final Integer point;
        private final Long missionCount;
    }
}
