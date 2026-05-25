package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMemberId(Long memberId);

    @Query("""
        SELECT r FROM Review r
        WHERE r.member.id = :memberId
        AND (:cursorId IS NULL OR r.id < :cursorId)
        ORDER BY r.id DESC
    """)
    List<Review> findReviewsByCursorId(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
        SELECT r FROM Review r
        WHERE r.member.id = :memberId
        AND (:cursorRating IS NULL OR r.rating <= :cursorRating)
        ORDER BY r.rating DESC
    """)
    List<Review> findReviewsByCursorRating(
            @Param("memberId") Long memberId,
            @Param("cursorRating") BigDecimal cursorRating,
            Pageable pageable
    );
}