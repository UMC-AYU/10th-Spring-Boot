package com.example.springboot.domain.member.converter;

import com.example.springboot.domain.member.dto.MemberReqDTO;
import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.enums.Gender;

public class MemberConverter {

    public static MemberResDTO.MyPage toMyPageDTO(Member member) {
        return MemberResDTO.MyPage.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .point(member.getPoint())
                .build();
    }

    public static Member toMember(MemberReqDTO.SignUp dto, String encodedPassword) {
        return Member.builder()
                .email(dto.email())
                .password(encodedPassword)
                .name(dto.name())
                .gender(dto.gender() != null
                        ? Gender.valueOf(dto.gender().toUpperCase())
                        : Gender.NONE)
                .birthDate(dto.birthDate())
                .address(dto.address())
                .snsType(dto.snsType())
                .build();
    }

    public static MemberResDTO.SignUp toSignUpDTO(Member member) {
        return MemberResDTO.SignUp.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public static MemberResDTO.Login toLoginDTO(Member member, String accessToken) {
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}