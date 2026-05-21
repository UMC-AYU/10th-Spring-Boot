package com.aim.umc10th.domain.mission.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.enums.MissionStatus;
import com.aim.umc10th.domain.mission.enums.Status;
import com.aim.umc10th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_mission")

public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MissionStatus status; //CHALLENGING, COMPLETE 등

    private LocalDateTime finishedAT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
