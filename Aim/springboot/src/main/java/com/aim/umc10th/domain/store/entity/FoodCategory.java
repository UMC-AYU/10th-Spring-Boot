package com.aim.umc10th.domain.store.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "food_category")
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_category_id") //ERD 기준 PG 매핑
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
}
