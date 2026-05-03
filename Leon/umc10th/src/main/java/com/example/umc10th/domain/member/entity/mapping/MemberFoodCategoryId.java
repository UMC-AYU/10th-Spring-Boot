package com.example.umc10th.domain.member.entity.mapping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberFoodCategoryId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "category_id")
    private Long categoryId;
}