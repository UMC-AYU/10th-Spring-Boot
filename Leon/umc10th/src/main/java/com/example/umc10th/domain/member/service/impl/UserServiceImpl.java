package com.example.umc10th.domain.member.service.impl;

import com.example.umc10th.domain.member.converter.UserConverter;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.FoodCategory;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.mapping.MemberFoodCategory;
import com.example.umc10th.domain.member.repository.FoodCategoryRepository;
import com.example.umc10th.domain.member.repository.MemberFoodCategoryRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.service.UserService;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MemberFoodCategoryRepository memberFoodCategoryRepository;

    private final UserConverter userConverter;

    @Override
    public MemberResponseDTO.GetMyInfo getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        return userConverter.toMyInfo(member);
    }

    @Override
    public Page<MemberResponseDTO.MyMission> getMyMissions(Long memberId, MissionStatus status, Pageable pageable) {

        Page<MemberMission> page = memberMissionRepository.findMyMissions(memberId, status, pageable);

        return page.map(userConverter::toMyMission);
    }

    @Override
    public List<MemberResponseDTO.MyReview> getMyReviews(Long memberId) {

        List<Review> reviews = reviewRepository.findByMemberId(memberId);

        return reviews.stream()
                .map(userConverter::toMyReview)
                .toList();
    }

    @Override
    @Transactional
    public void setPreferences(Long memberId, List<Long> categoryIds) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        memberFoodCategoryRepository.deleteByMemberId(memberId);

        List<FoodCategory> categories = foodCategoryRepository.findByIdIn(categoryIds);

        for (FoodCategory category : categories) {
            MemberFoodCategory mfc = MemberFoodCategory.of(member, category);
            memberFoodCategoryRepository.save(mfc);
        }
    }
}