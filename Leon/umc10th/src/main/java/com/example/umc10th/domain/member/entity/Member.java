package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String socialUid;
    private String socialType;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;
    private String address;
    private String email;
    private Integer point;

    @OneToMany(mappedBy = "member")
    private List<MemberMission> memberMissions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();


    public static Member create(
            String password,
            String name,
            Gender gender,
            LocalDate birthDate,
            String address,
            String email
    ) {
        Member member = new Member();
        member.password = password;
        member.socialUid = "temp";
        member.socialType = "temp";
        member.name = name;
        member.gender = gender;
        member.birthDate = birthDate;
        member.address = address;
        member.email = email;
        member.point = 0;
        return member;
    }

    public void encodePassword(String password) {
        this.password = password;
    }
}