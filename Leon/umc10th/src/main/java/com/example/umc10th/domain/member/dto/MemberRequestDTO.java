package com.example.umc10th.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class Signup {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String gender;

        @NotNull
        private LocalDate birthDate;

        @NotBlank
        private String address;

    }

    @Getter
    public static class Login {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Getter
    public static class SetPreferences {

        @NotEmpty
        private List<Long> categoryIds;
    }

    @Getter
    public static class MyMissionRequest {

        @NotNull
        private Long memberId;
    }
}