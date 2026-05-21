package com.example.springboot.domain.member.converter;

import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResDTO.MyPage toMyPageDTO(Member member) {
        return MemberResDTO.MyPage.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .point(member.getPoint())
                .build();
    }
}