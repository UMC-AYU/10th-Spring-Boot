package org.example.swaggerpr.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.swaggerpr.mission.entity.mapping.MemberMission;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private Integer rewardPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 회원별 진행 상태는 Mission 자체가 아니라 MemberMission에 저장한다.
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberMission> memberMissions = new ArrayList<>();
}
