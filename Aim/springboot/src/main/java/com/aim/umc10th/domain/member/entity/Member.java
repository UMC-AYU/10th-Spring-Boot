package com.aim.umc10th.domain.member.entity;

import com.aim.umc10th.domain.member.enums.Gender;
import com.aim.umc10th.domain.common.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") //DB의 member_id 컬럼을 Java의 id 변수에 매핑하자!
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "point")
    private Integer point;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="birth")
    private LocalDate birth;

    @Column(name = "address")
    @Enumerated(EnumType.STRING)
    private Address address;
}
