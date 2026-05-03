package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.mapping.MemberFoodCategory;
import com.example.umc10th.domain.member.entity.mapping.MemberFoodCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFoodCategoryRepository extends JpaRepository<MemberFoodCategory, MemberFoodCategoryId> {

    void deleteByMemberId(Long memberId);
}