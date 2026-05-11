package org.example.swaggerpr.member.entity.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.example.swaggerpr.member.entity.FoodCategory;
import org.example.swaggerpr.member.entity.Member;

@Entity
@Table(name = "member_prefer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberFoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_prefer_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;
}
