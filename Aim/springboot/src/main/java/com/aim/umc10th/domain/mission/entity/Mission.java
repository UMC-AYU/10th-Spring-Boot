package com.aim.umc10th.domain.mission.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import com.aim.umc10th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "mission")

public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Mission_id")
    private Long id;

    @Column(nullable = false)
    private Long reward; //미션 포인트

    @Column(nullable = false, length = 1000)
    private String missionSpec; //미션 내용

    @Column(nullable = false)
    private LocalDateTime deadline; //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}

