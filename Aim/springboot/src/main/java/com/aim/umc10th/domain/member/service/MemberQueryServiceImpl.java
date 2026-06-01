package com.aim.umc10th.domain.member.service;

import ch.qos.logback.core.status.ErrorStatus;
import com.aim.umc10th.domain.review.entity.Review;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.exception.MemberException;
import com.aim.umc10th.domain.member.repository.MemberMissionRepository;
import com.aim.umc10th.domain.member.repository.MemberRepository;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import com.aim.umc10th.domain.review.repository.ReviewRepository;
import com.aim.umc10th.global.config.apiPayload.exception.MemberHandler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public Page<MemberMission> getMissionList(Long memberId, MissionStatus status, Integer page, Integer size){
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

    @Override
    public Page<MemberMission> getChallengingMissionList(Long memberId, Integer page){
        //1. 멤버가 존재하는지 확인 (없으면 에러 핸들러 호출)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberHandler(GeneralErrorCode.MEMBER_NOT_FOUND));

        //2. 해당 멤버의 미션 중 CHALLENGING 상태인 것만 페이징해서 가져오기
        return memberMissionRepository.findAllByMemberAndStatus(
                member,
                MissionStatus.CHALLENGING,
                PageRequest.of(page, 10)//페이지 사이즈 10으로 잡기
        );
    }

    @Override
    public Slice<Review> getMyReviewList(Long memberId, String target, Long cursor, Float scoreCursor, Integer page){
        System.out.println("DEBUG >>> memberId: " + memberId + ", target: " + target + ", cursor: " + cursor);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new MemberHandler(GeneralErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0,10); //커서 기반이므로 페이지 번호는 항상 0으로...

        //별점순으로 조회
        if("score".equalsIgnoreCase(target)){
            //커서가 null이면 5.1(최대 별점보다 큰 값)부터 시작
            Float targetScore = (scoreCursor == null) ? 5.1f : scoreCursor;
            return reviewRepository.findAllByMemberAndScoreLessThanOrderByScoreDescIdDesc(member, targetScore, pageable);
        }

        // 기본은 최신순(ID순)으로 조회하기
        // 커서가 null이면 Long.MAX_VALUE(가장 큰 숫자)부터 시작
        // 만약 0L로 되어 있으면 0보다 작은 ID는 없으니 아무것도 안나옴
        Long targetCursor = (cursor == null || cursor == 0) ? Long.MAX_VALUE : cursor;

        System.out.println("DEBUG >>> 최종 적용 targetCursor: " + targetCursor);
        return reviewRepository.findAllByMemberAndIdLessThanOrderByIdDesc(member, targetCursor, pageable);
    }
}
