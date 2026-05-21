package com.aim.umc10th.domain.member.entity;

import com.aim.umc10th.domain.common.entity.BaseEntity;
import com.aim.umc10th.domain.member.enums.Gender;
import com.aim.umc10th.domain.common.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") //DB의 member_id 컬럼을 Java의 id 변수에 매핑하자!
    private Long id;

    @Column(nullable = false, length = 20) //이름은 서비스의 필수 데이터이다.
    private String name;

    @Column(nullable = false, unique = true, length = 50) //로그인 아이디로 쓰인다면 비어있어도 안되고 중복도 안된다.
    private String email;

    @Column(columnDefinition = "INTEGER DEFAULT 0") //Point 기본값 필요
    private Long point;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    //mappedBy는 나는 연관관계의 주인은 아니고, MemberPrefer에 있는 member필드에 의해 매핑되었다는 뜻이다.
    //cascade = CascadeType.ALL: 회원이 저장되거나 삭제될 때, 그 회원의 선호 카테고리 정보도 함께 처리되도록 하는 설정
    private List<MemberPrefer> memberPreferList = new ArrayList<>();
}
