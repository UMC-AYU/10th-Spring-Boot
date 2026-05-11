package org.example.swaggerpr.mission.entity.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.mission.enums.Status;

@Entity
@Table(name = "member_mission")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateStatus(Status status) {
        // 미션 성공 누르기 API는 매핑 엔티티의 진행 상태만 변경하면 된다.
        this.status = status;
    }
}
