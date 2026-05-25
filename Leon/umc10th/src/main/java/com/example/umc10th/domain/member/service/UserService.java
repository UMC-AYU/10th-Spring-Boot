package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.FoodCategory;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.entity.mapping.MemberFoodCategory;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.FoodCategoryRepository;
import com.example.umc10th.domain.member.repository.MemberFoodCategoryRepository;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.global.enums.MissionSortType;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.enums.ReviewSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.umc10th.global.sort.SortUtil.getMissionSort;
import static com.example.umc10th.global.sort.SortUtil.getReviewSort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final MemberFoodCategoryRepository memberFoodCategoryRepository;

    private final MemberConverter memberConverter;
    private final ReviewConverter reviewConverter;

    public MemberResponseDTO.GetMyInfo getMyInfo(Member member) {
        return memberConverter.toMyInfo(member);
    }

    public MemberResponseDTO.Pagination<MemberResponseDTO.MyMission> getMyMissions(
            Long memberId,
            MissionStatus status,
            MissionSortType sort,
            Pageable pageable
    ) {

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                getMissionSort(sort)
        );

        Page<MemberMission> page =
                memberMissionRepository.findMyMissions(
                        memberId,
                        status,
                        sortedPageable
                );

        return memberConverter.toPagination(
                page.map(memberConverter::toMyMission)
        );
    }

    public MemberResponseDTO.Pagination<MemberResponseDTO.MyReview> getMyReviews(
            Long memberId,
            ReviewSortType sort,
            Pageable pageable
    ) {

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                getReviewSort(sort)
        );

        Page<Review> page =
                reviewRepository.findByMemberId(memberId, sortedPageable);

        return memberConverter.toPagination(
                page.map(reviewConverter::toMyReview)
        );
    }

    @Transactional
    public void setCategories(Long memberId, List<Long> categoryIds) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        memberFoodCategoryRepository.deleteByMemberId(memberId);

        List<FoodCategory> categories = foodCategoryRepository.findByIdIn(categoryIds);

        if (categories.size() != categoryIds.size()) {
            throw new MemberException(MemberErrorCode.CATEGORY_NOT_FOUND);
        }

        for (FoodCategory category : categories) {
            MemberFoodCategory mfc = MemberFoodCategory.of(member, category);
            memberFoodCategoryRepository.save(mfc);
        }
    }

}