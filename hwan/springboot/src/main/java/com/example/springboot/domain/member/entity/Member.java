package com.example.springboot.domain.member.entity;

import com.example.springboot.domain.member.entity.mapping.UserFoodPreference;
import com.example.springboot.domain.member.enums.Gender;
import com.example.springboot.domain.member.enums.SnsType;
import com.example.springboot.domain.mission.entity.mapping.UserMission;
import com.example.springboot.domain.review.entity.Review;
import com.example.springboot.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.NONE;

    private LocalDate birthDate;

    private String address;

    @Builder.Default
    private Integer point = 0;

    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserFoodPreference> foodPreferences = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserMission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}