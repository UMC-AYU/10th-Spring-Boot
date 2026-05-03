package com.example.springboot.domain.member.dto;

import lombok.Builder;

public class MemberResDTO {

    @Builder
    public record SignUp(
            Long userId,
            String email,
            String name
    ) {}

    @Builder
    public record MyPage(
            Long memberId,
            String name,
            String email,
            Integer point
    ) {}
}