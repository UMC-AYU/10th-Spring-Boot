package org.example.swaggerpr.review.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.exception.code.MemberErrorCode;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.mission.exception.code.MissionErrorCode;
import org.example.swaggerpr.mission.repository.MissionRepository;
import org.example.swaggerpr.review.converter.ReviewConverter;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.entity.Review;
import org.example.swaggerpr.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public ReviewResDto.CreateReviewResultDto createReview(Long userId, Long missionId, ReviewReqDto.CreateReviewDto dto) {
        Member member = memberRepository.findById(userId) // 회원 조회
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));
        Mission mission = missionRepository.findById(missionId) // 미션 조회
                .orElseThrow(() -> new ProjectException(MissionErrorCode.NOT_FOUND));

        Review review = reviewRepository.save(Review.builder() // 리뷰 생성 및 저장
                .member(member)
                // missionId로 Mission을 찾고 해당 Mission의 Store를 리뷰의 FK로 저장한다.
                .store(mission.getStore())
                .score(dto.getScore().floatValue())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build());

        return ReviewConverter.toCreateReviewResultDto(review, missionId); // 결과 반환
    }
}
