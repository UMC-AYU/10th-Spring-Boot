package com.example.umc10th.domain.member.entity.mapping;

import com.example.umc10th.domain.member.entity.FoodCategory;
import com.example.umc10th.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_food_category")
public class MemberFoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_food_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FoodCategory category;

    public static MemberFoodCategory of(Member member, FoodCategory category) {
        MemberFoodCategory entity = new MemberFoodCategory();
        entity.member = member;
        entity.category = category;
        return entity;
    }
}