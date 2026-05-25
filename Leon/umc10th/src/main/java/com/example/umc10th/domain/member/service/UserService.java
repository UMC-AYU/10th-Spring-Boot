package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.global.enums.ReviewSortType;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    MemberResponseDTO.GetMyInfo getMyInfo(Long memberId);

    MemberResponseDTO.Pagination<MemberResponseDTO.MyMission> getMyMissions(Long memberId, MissionStatus status, Pageable pageable);

    MemberResponseDTO.CursorPage<MemberResponseDTO.MyReview> getMyReviews(Long memberId, Long cursorId, BigDecimal cursorRating, Integer size, ReviewSortType sort);

    void setPreferences(Long memberId, List<Long> categoryIds);
}