package com.example.springboot.domain.member.dto;

import lombok.Builder;

public class MemberResDTO {

    @Builder
    public record SignUp(
            Long memberId,
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

    @Builder
    public record Login(
            String accessToken,
            Long memberId,
            String email,
            String name
    ) {}
}