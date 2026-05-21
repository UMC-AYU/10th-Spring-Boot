package com.example.springboot.domain.mission.entity.mapping;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.mission.entity.Mission;
import com.example.springboot.domain.mission.enums.MissionStatus;
import com.example.springboot.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_mission")
public class UserMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MissionStatus status = MissionStatus.IN_PROGRESS;

    public void changeStatus(MissionStatus status) {
        this.status = status;
    }
}