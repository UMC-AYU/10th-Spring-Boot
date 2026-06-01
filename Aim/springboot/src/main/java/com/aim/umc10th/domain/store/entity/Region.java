package com.aim.umc10th.domain.store.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "region")
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_Id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    //양방향 매핑: 1:N 관계
    // 지역 하나에 여러 가게 속할 수 있음.
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Store> storeList = new ArrayList<>();
}
