package com.example.umc10th.domain.member.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class Signup {
        private String email;
        private String password;
        private String name;
        private String gender;
        private LocalDate birthDate;
        private String address;
        private Boolean serviceAgree;
    }

    @Getter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    public static class SetPreferences {
        private List<Long> categoryIds;
    }
}