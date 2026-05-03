package com.example.springboot.domain.member.entity;

import com.example.springboot.domain.member.entity.mapping.UserFoodPreference;
import com.example.springboot.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food_category")
public class FoodCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserFoodPreference> userFoodPreferences = new ArrayList<>();
}