package com.example.springboot.domain.store.repository;

import com.example.springboot.domain.store.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
