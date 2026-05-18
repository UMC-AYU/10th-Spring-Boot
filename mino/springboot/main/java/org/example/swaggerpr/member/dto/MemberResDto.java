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
    public static class MyPageDto {
        private final Long userId;
        private final String name;
        private final String email;
        private final String phone;
        private final Integer point;
        private final Long missionCount;
    }
}
