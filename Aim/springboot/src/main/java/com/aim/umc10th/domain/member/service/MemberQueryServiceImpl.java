package com.aim.umc10th.domain.member.service;

import ch.qos.logback.core.status.ErrorStatus;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.exception.MemberException;
import com.aim.umc10th.domain.member.repository.MemberMissionRepository;
import com.aim.umc10th.domain.member.repository.MemberRepository;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import com.aim.umc10th.domain.review.repository.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.aim.umc10th.domain.member.enums.MissionStatus;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Page<MemberMission> getMissionList(Long memberId, MissionStatus status, Integer page){
        //1.회원 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));

        return memberMissionRepository.findAllByMemberAndStatus(member, status, PageRequest.of(page, 10));
    }

    @Override
    public Member getMyPageInfo(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberException(GeneralErrorCode.MEMBER_NOT_FOUND));
    }

    // 리뷰 개수는 테이블에 없으므로 서비스 로직에서 직접 계산한다.
    public Integer getReviewCount(Long memberId) {
        // ReviewRepository에 우리가 만들 countByMemberId 호출
        return reviewRepository.countByMemberId(memberId);
    }
}
