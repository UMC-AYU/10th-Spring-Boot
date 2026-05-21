package com.example.umc10th.domain.review.service.impl;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.domain.review.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    private final ReviewConverter reviewConverter;

    @Override
    @Transactional
    public ReviewResponseDTO.ReviewInfo createReview(Long memberId, Long storeId, ReviewRequestDTO.CreateReview dto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게 없음"));

        Review review = reviewConverter.toEntity(member, store, dto);

        Review saved = reviewRepository.save(review);

        return reviewConverter.toReviewInfo(saved);
    }
}