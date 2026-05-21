package com.aim.umc10th.domain.member.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import com.aim.umc10th.domain.store.entity.FoodCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_prefer_category")
public class MemberPrefer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_prefer_id") //ERD 기준 PK 매핑
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩 (권장)
    @JoinColumn(name = "member_id") //외래키 컬럼명 설정
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id") //FoodCategory와의 연결점
    private FoodCategory foodCategory;
}
