package com.aim.umc10th.domain.review.service;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.repository.MemberRepository;
import com.aim.umc10th.domain.review.entity.Review;
import com.aim.umc10th.domain.review.repository.ReviewRepository;
import com.aim.umc10th.domain.store.entity.Store;
import com.aim.umc10th.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional

public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override // 인터페이스의 메서드를 실제로 구현함
    public Review createReview(Long memberId, Long storeId, String body, Float score) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("해당 가게가 존재하지 않습니다."));

        Review review = Review.builder()
                .body(body)
                .score(score)
                .member(member)
                .store(store)
                .build();

        return reviewRepository.save(review);
    }
}
