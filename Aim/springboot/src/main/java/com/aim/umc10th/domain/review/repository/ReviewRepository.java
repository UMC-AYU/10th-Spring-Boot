package com.aim.umc10th.domain.review.repository;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.review.entity.Review;
import com.aim.umc10th.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //가게별 리뷰 조회(페이징 포함)
    @Query("SELECT r FROM Review r WHERE r.store = :store")
    Page<Review> findAllByStore(@Param("store") Store store, Pageable pageable);

    //특정 멤버가 작성한 리뷰의 총 개수를 반환하는 메서드
    Integer countByMemberId(Long memberId);

    //7주차 미션2
    // 최신순 (ID 순서) 커서 기반
    // lastId보다 작은 ID를 가진 리뷰를 N개 가져옴
    Slice<Review> findAllByMemberAndIdLessThanOrderByIdDesc(Member member, Long lastId, Pageable pageable);

    // 별점순 커서 기반 (만약 별점이 같다면 그때는 어떻게 해야하지...? -> 일단은 동일하면 ID 순으로 정렬...?)
    Slice<Review> findAllByMemberAndScoreLessThanOrderByScoreDescIdDesc(Member member, Float score, Pageable pageable);
}
