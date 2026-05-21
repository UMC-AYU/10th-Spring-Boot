package com.example.springboot.domain.mission.repository;

import com.example.springboot.domain.mission.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}