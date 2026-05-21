package com.aim.umc10th.domain.member.service;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.enums.MissionStatus;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;

public interface MemberQueryService {
    Page<MemberMission> getMissionList(Long memberId, MissionStatus status, Integer page);

    //마이페이지 조회를 위해 추가했던 메소드
    Member getMyPageInfo(Long memberId);

    //특정 회원 리뷰 개수를 가져오는 기능
    Integer getReviewCount(Long memberId);
}
