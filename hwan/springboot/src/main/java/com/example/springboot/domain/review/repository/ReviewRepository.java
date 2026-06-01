package com.example.springboot.domain.review.repository;

import com.example.springboot.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Slice<Review> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);
    Slice<Review> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long cursor, Pageable pageable);
    Slice<Review> findByMemberIdOrderByScoreDescIdDesc(Long memberId, Pageable pageable);
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId " +
            "AND (r.score < :scoreCursor OR (r.score = :scoreCursor AND r.id < :idCursor)) " +
            "ORDER BY r.score DESC, r.id DESC")
    Slice<Review> findByMemberIdWithRatingCursor(
            @Param("memberId") Long memberId,
            @Param("scoreCursor") Float scoreCursor,
            @Param("idCursor") Long idCursor,
            Pageable pageable);
}