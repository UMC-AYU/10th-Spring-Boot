package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewRequestDTO;
import com.example.umc10th.domain.review.dto.ReviewResponseDTO;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public Review toEntity(Member member, Store store, ReviewRequestDTO.CreateReview dto) {
        return Review.create(
                member,
                store,
                dto.getRating(),
                dto.getContent()
        );
    }

    public ReviewResponseDTO.ReviewInfo toReviewInfo(Review review) {
        return ReviewResponseDTO.ReviewInfo.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public MemberResponseDTO.MyReview toMyReview(Review review) {
        return MemberResponseDTO.MyReview.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public <T> ReviewResponseDTO.Pagination<T> toPagination(Page<T> page) {

        return ReviewResponseDTO.Pagination.<T>builder()
                .data(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .build();
    }
}