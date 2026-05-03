package com.example.springboot.domain.member.dto;

import com.example.springboot.domain.member.enums.SnsType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record SignUp(
            @NotBlank @Email String email,
            @NotBlank String password,
            @NotBlank String name,
            String gender,
            LocalDate birthDate,
            String address,
            List<String> foodTypes,
            SnsType snsType
    ) {}

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
}