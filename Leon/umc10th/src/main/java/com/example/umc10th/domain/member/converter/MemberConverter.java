package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.global.security.dto.OAuthDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member toMember(MemberRequestDTO.Signup dto) {
        return Member.create(
                dto.getPassword(),
                dto.getName(),
                dto.getGender(),
                dto.getBirthDate(),
                dto.getAddress(),
                dto.getEmail()
        );
    }

    public Member toMember(OAuthDTO dto) {
        return Member.createOAuthMember(
                dto.getSocialType(),
                dto.getSocialUid(),
                dto.getName(),
                dto.getSocialEmail()
        );
    }

    public MemberResponseDTO.Login toLogin(
            Member member,
            String accessToken
    ) {
        return MemberResponseDTO.Login.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .accessToken(accessToken)
                .build();
    }

    public MemberResponseDTO.GetMyInfo toMyInfo(Member member) {
        return MemberResponseDTO.GetMyInfo.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .build();
    }

    public MemberResponseDTO.MyMission toMyMission(MemberMission mm) {
        return MemberResponseDTO.MyMission.builder()
                .missionId(mm.getMission().getId())
                .status(mm.getStatus().name())
                .build();
    }

    public <T> MemberResponseDTO.Pagination<T> toPagination(Page<T> page) {
        return MemberResponseDTO.Pagination.<T>builder()
                .data(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .build();
    }
}