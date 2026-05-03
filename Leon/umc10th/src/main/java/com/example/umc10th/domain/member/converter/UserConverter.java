package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserConverter {

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

    public MemberResponseDTO.MyReview toMyReview(Review review) {
        return MemberResponseDTO.MyReview.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(BigDecimal.valueOf(review.getRating().intValue()))
                .build();
    }
}