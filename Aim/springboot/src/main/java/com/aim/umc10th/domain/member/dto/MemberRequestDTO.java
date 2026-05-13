package com.aim.umc10th.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDTO {
    //마이페이지
    public record GetInfo(
            Long member_id //API 명세서에서 Request Body는 id만 받기로 함
    ){}

    // 회원가입 요청
    @Getter
    public static class joinDTO{
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email;

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String name;

        @NotNull(message = "성별은 필수 선택 항목입니다.")
        Integer gender;

        @NotNull(message = "생년월인은 필수입니다.")
        LocalDate birthDate;

        @NotNull(message = "주소는 필수 입력 항목입니다.")
        String address;

        @NotEmpty(message = "선호 카테고리를 최소 하나 이상 선택해주세요.")
        List<Long> preferCategory; //선호 카테고리 ID 리스트
    }

    //로그인 요청
    @Getter
    public static class LoginDTO{
        String email;
        String password;
    }


    //미션 1 진행
    @Getter
    public static class MissionListDTO{
        @NotNull
        Long memberId; // 사용자 ID를 바디에서 받음
    }
}
