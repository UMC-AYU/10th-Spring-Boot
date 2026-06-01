package com.example.springboot.domain.review.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.domain.mission.entity.Store;
import com.example.springboot.domain.mission.repository.StoreRepository;
import com.example.springboot.domain.review.converter.ReviewConverter;
import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.entity.Review;
import com.example.springboot.domain.review.exception.ReviewErrorCode;
import com.example.springboot.domain.review.exception.ReviewException;
import com.example.springboot.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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

    @Transactional(readOnly = true)
    public ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItem> getMyReviews(
            Long memberId,
            Integer pageSize,
            String cursor,
            String query
    ) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        boolean isFirst = "-1".equals(cursor);

        Slice<Review> slice = switch (query.toLowerCase()) {

            case "id" -> isFirst
                    ? reviewRepository.findByMemberIdOrderByIdDesc(memberId, pageRequest)
                    : reviewRepository.findByMemberIdAndIdLessThanOrderByIdDesc(
                    memberId, parseId(cursor), pageRequest);

            case "rating" -> isFirst
                    ? reviewRepository.findByMemberIdOrderByScoreDescIdDesc(memberId, pageRequest)
                    : reviewRepository.findByMemberIdWithRatingCursor(
                    memberId, parseScore(cursor), parseId(cursor), pageRequest);

            default -> throw new ReviewException(ReviewErrorCode.INVALID_RATING);
        };

        return ReviewConverter.toCursorPagination(slice, query);
    }

    private Long parseId(String cursor) {
        String[] parts = cursor.split(":");
        return Long.parseLong(parts[parts.length - 1]);
    }

    private Float parseScore(String cursor) {
        return Float.parseFloat(cursor.split(":")[1]);
    }
}