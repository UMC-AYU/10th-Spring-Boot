package com.example.springboot.domain.member.dto;

import com.example.springboot.domain.member.enums.SnsType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record SignUp(
            @NotBlank @Email String email,
            @NotBlank String password,
            @NotBlank String name,
            @Pattern(regexp = "^(?i)(MALE|FEMALE|NONE)$") String gender,
            @Past LocalDate birthDate,
            String address,
            List<String> foodTypes,
            SnsType snsType
    ) {}

    public record Login(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}
}