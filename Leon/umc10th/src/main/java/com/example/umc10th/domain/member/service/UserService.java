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
import com.example.umc10th.domain.member.service.UserService;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.converter.ReviewConverter;
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

import java.math.BigDecimal;
import java.util.List;

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

    public MemberResponseDTO.GetMyInfo getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return memberConverter.toMyInfo(member);
    }

    public MemberResponseDTO.Pagination<MemberResponseDTO.MyMission> getMyMissions(Long memberId, MissionStatus status, Pageable pageable) {

        Page<MemberMission> page = memberMissionRepository.findMyMissions(memberId, status, pageable);

        return memberConverter.toPagination(page.map(memberConverter::toMyMission));
    }

    public MemberResponseDTO.CursorPage<MemberResponseDTO.MyReview> getMyReviews(Long memberId, Long cursorId, BigDecimal cursorRating, Integer size, ReviewSortType sort) {

        List<Review> reviews;

        if (sort == ReviewSortType.ID) {

            reviews = reviewRepository.findReviewsByCursorId(
                    memberId,
                    cursorId,
                    PageRequest.of(0, size + 1)
            );

        } else {

            reviews = reviewRepository.findReviewsByCursorRating(
                    memberId,
                    cursorRating,
                    PageRequest.of(0, size + 1)
            );
        }

        boolean hasNext = reviews.size() > size;

        if (hasNext) {
            reviews.remove(size);
        }

        List<MemberResponseDTO.MyReview> data =
                reviews.stream()
                        .map(reviewConverter::toMyReview)
                        .toList();

        Long nextCursorId = null;
        BigDecimal nextCursorRating = null;

        if (!reviews.isEmpty()) {

            Review lastReview = reviews.getLast();

            nextCursorId = lastReview.getId();
            nextCursorRating = lastReview.getRating();
        }

        return MemberResponseDTO.CursorPage.<MemberResponseDTO.MyReview>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursorId(nextCursorId)
                .nextCursorRating(nextCursorRating)
                .build();
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