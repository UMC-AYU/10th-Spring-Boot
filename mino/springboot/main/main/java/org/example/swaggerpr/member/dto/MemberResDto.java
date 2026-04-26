package org.example.swaggerpr.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResDto {
    // static을 사용하는 이유 - 비즈니스 로직별로 필요한 데이터를 나누고 불필요한 인스턴스 생성을 방지하기 위해
    // 회원가입 응답
    @Getter
    @Builder // 필드 이름 기반으로 객체 생성 스타일을 가독성 좋게 만듦
    public static class SignupResultDto {
        private Long userId;
        private String email;
        private String name;
    }

    // 마이페이지 응답
    @Getter
    @Builder
    public static class MyPageDto {
        private Long userId;
        private String name;
        private String email;
        private String phone;

        // 필요하면 추가
        private Integer point;
        private Integer missionCount;
    }
}
