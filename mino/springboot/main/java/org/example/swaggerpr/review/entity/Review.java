package org.example.swaggerpr.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.mission.entity.Store;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 현재 API 명세에는 텍스트 리뷰만 존재
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Float score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 리뷰 작성 API는 missionId를 받지만 ERD에서는 Review가 Store에 연결된다.
    // 서비스에서 missionId로 Mission을 조회한 뒤 Mission.store를 저장하도록 설계했다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}
