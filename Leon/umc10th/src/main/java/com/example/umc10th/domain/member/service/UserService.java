package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.global.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    MemberResponseDTO.GetMyInfo getMyInfo(Long memberId);

    Page<MemberResponseDTO.MyMission> getMyMissions(Long memberId, MissionStatus status, Pageable pageable);

    List<MemberResponseDTO.MyReview> getMyReviews(Long memberId);

    void setPreferences(Long memberId, List<Long> categoryIds);
}