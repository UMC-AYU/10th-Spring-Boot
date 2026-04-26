package com.example.springboot.domain.member.dto;

import com.example.springboot.domain.member.enums.SnsType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserReqDTO {

    public record SignUp(
            @NotBlank @Email String email,
            @NotBlank String password,
            @NotBlank String name,
            String gender,
            String birthDate,
            String address,
            List<String> foodTypes,
            SnsType snsType
    ) {}
}