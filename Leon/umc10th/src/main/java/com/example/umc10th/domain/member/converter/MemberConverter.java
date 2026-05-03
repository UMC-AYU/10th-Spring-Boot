package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member toMember(MemberRequestDTO.Signup dto) {
        return Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .socialType("LOCAL")
                .socialUid(dto.getEmail()) // 임시
                .point(0)
                .build();
    }

    public MemberResponseDTO.GetMyInfo toGetMyInfo(Member member) {
        return MemberResponseDTO.GetMyInfo.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .build();
    }

    public MemberResponseDTO.Login toLogin(Member member) {
        return MemberResponseDTO.Login.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}