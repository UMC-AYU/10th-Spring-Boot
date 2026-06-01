package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.store.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.enums.ReviewSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.umc10th.global.sort.SortUtil.getReviewSort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    private final ReviewConverter reviewConverter;

    public ReviewResponseDTO.Pagination<ReviewResponseDTO.ReviewInfo> getReviews(
            Long storeId,
            ReviewSortType sort,
            Pageable pageable
    ) {

        storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                getReviewSort(sort)
        );

        Page<Review> page =
                reviewRepository.findByStoreId(
                        storeId,
                        sortedPageable
                );

        return reviewConverter.toPagination(
                page.map(reviewConverter::toReviewInfo)
        );
    }

    @Transactional
    public ReviewResponseDTO.ReviewInfo createReview(Long memberId, Long storeId, ReviewRequestDTO.CreateReview dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ReviewException(ReviewErrorCode.REVIEW_MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));

        Review review = reviewConverter.toEntity(member, store, dto);

        Review saved = reviewRepository.save(review);

        return reviewConverter.toReviewInfo(saved);
    }

}