package org.example.swaggerpr.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.swaggerpr.member.entity.mapping.MemberFoodCategory;
import org.example.swaggerpr.member.enums.Gender;
import org.example.swaggerpr.member.enums.UserState;
import org.example.swaggerpr.mission.entity.mapping.MemberMission;
import org.example.swaggerpr.review.entity.Review;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    // 추후 암호화 추가
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(nullable = false, length = 100)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private UserState status;

    @Column(nullable = false)
    private Integer points;

    // 회원별 미션 진행 상태를 조회하기 위해 MemberMission과 1:N으로 연결한다.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberMission> memberMissions = new ArrayList<>();

    // 마이페이지/리뷰 내역 확장을 고려해 ERD의 Review.member_id FK를 양방향으로 열어둔다.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    // 회원 선호 카테고리 매핑 엔티티로 표현한다.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberFoodCategory> preferCategories = new ArrayList<>();
}
