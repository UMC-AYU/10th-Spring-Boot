package org.example.swaggerpr.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberReqDto {
    @Getter //불필요한 코드를 쓸필요가 없게 만듦
    @NoArgsConstructor(access = AccessLevel.PROTECTED) //Json -> Jackson 변환 과정 시 필요
    // static을 사용하는 이유 - 비즈니스 로직별로 필요한 데이터를 나누고 불필요한 인스턴스 생성을 방지하기 위해
    // 회원가입 요청
    public static class SignupDto {
        private String email;
        private String password;
        private String name;
        private String phone;
    }
}
