package org.example.swaggerpr.review.repository;

import org.example.swaggerpr.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
            select r
            from Review r
            join fetch r.store s
            where r.member.id = :memberId
              and (:cursorId is null or r.id > :cursorId)
            order by r.id asc
            """)
    List<Review> findMyReviewsOrderById(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("""
            select r
            from Review r
            join fetch r.store s
            where r.member.id = :memberId
              and (
                    :cursorScore is null
                    or r.score > :cursorScore
                    or (r.score = :cursorScore and r.id > :cursorId)
                  )
            order by r.score asc, r.id asc
            """)
    List<Review> findMyReviewsOrderByScore(
            @Param("memberId") Long memberId,
            @Param("cursorScore") Float cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
