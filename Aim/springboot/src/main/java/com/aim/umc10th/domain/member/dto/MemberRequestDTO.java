package com.aim.umc10th.domain.member.dto;

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
        String email;
        String name;
        Integer gender;
        LocalDate birthDate;
        String address;
        List<Long> preferCategory; //선호 카테고리 ID 리스트
    }

    //로그인 요청
    @Getter
    public static class LoginDTO{
        String email;
        String password;
    }
}
