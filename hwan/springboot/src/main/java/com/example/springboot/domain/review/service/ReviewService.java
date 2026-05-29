package com.example.springboot.domain.review.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.exception.MemberErrorCode;
import com.example.springboot.domain.member.exception.MemberException;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.domain.review.converter.ReviewConverter;
import com.example.springboot.domain.review.dto.ReviewReqDTO;
import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.entity.Review;
import com.example.springboot.domain.review.exception.ReviewErrorCode;
import com.example.springboot.domain.review.exception.ReviewException;
import com.example.springboot.domain.review.repository.ReviewRepository;
import com.example.springboot.domain.store.entity.Store;
import com.example.springboot.domain.store.exception.StoreErrorCode;
import com.example.springboot.domain.store.exception.StoreException;
import com.example.springboot.domain.store.repository.StoreRepository;
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
                .orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));

        Review review = Review.builder()
                .store(store)
                .member(member)
                .score(dto.score())
                .content(dto.content())
                .build();

        // 가게 평균 점수 업데이트
        int prevCount = store.getReviews().size();
        Review saved = reviewRepository.save(review);
        float newAvg = (store.getScore() * prevCount + saved.getScore()) / (prevCount + 1);
        store.updateScore(newAvg);

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
        String normalizedQuery = query.toLowerCase();

        Slice<Review> slice = switch (normalizedQuery) {

            case "id" -> isFirst
                    ? reviewRepository.findByMemberIdOrderByIdDesc(memberId, pageRequest)
                    : reviewRepository.findByMemberIdAndIdLessThanOrderByIdDesc(
                    memberId, parseId(cursor), pageRequest);

            case "rating" -> isFirst
                    ? reviewRepository.findByMemberIdOrderByScoreDescIdDesc(memberId, pageRequest)
                    : reviewRepository.findByMemberIdWithRatingCursor(
                    memberId, parseScore(cursor), parseId(cursor), pageRequest);

            default -> throw new ReviewException(ReviewErrorCode.INVALID_QUERY);
        };

        return ReviewConverter.toCursorPagination(slice, normalizedQuery);
    }

    private Long parseId(String cursor) {
        String[] parts = cursor.split(":");
        try {
            return Long.parseLong(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }
    }

    private Float parseScore(String cursor) {
        if (cursor == null || !cursor.contains(":")) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }
        String[] parts = cursor.split(":");
        if (parts.length < 2) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }
        try {
            return Float.parseFloat(parts[1]);
        } catch (NumberFormatException e) {
            throw new ReviewException(ReviewErrorCode.INVALID_CURSOR);
        }
    }
}
