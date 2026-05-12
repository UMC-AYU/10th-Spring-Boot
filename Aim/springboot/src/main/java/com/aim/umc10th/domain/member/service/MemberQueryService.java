package com.aim.umc10th.domain.member.service;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.enums.MissionStatus;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import com.aim.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface MemberQueryService {
    Page<MemberMission> getMissionList(Long memberId, MissionStatus status, Integer page);

    //마이페이지 조회를 위해 추가했던 메소드
    Member getMyPageInfo(Long memberId);

    //특정 회원 리뷰 개수를 가져오는 기능
    Integer getReviewCount(Long memberId);

    //내가 진행 중인 미션 목록 가져오기
    Page<MemberMission> getChallengingMissionList(Long memberId, Integer page);

    // 7주차 미션2 (정렬 기준에 따라 동적으로 처리하는 로직)
    Slice<Review> getMyReviewList(Long memberId, String target, Long cursor, Float scoreCursor, Integer page);
}
