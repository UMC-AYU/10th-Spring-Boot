package com.example.springboot.domain.member.dto;

import lombok.Builder;

public class UserResDTO {

    @Builder
    public record SignUp(
            Long userId,
            String email,
            String name
    ) {}
}