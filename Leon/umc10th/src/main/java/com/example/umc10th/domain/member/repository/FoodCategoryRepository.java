package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    List<FoodCategory> findByIdIn(List<Long> ids);
}