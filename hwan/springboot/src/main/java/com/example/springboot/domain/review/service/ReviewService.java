package com.example.springboot.domain.review.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.domain.mission.entity.Store;
import com.example.springboot.domain.mission.repository.StoreRepository;
import com.example.springboot.domain.review.converter.ReviewConverter;
import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.entity.Review;
import com.example.springboot.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewResDTO.CreateResult createReview(Long storeId, Long memberId, ReviewReqDTO.Create dto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Review review = Review.builder()
                .store(store)
                .member(member)
                .score(dto.score())
                .content(dto.content())
                .build();

        Review saved = reviewRepository.save(review);
        return ReviewConverter.toCreateDTO(saved);
    }
}