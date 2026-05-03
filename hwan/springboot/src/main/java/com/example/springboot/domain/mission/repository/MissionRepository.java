package com.example.springboot.domain.mission.repository;

import com.example.springboot.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m " +
            "JOIN FETCH m.store s " +
            "WHERE s.region.id = :regionId")
    Page<Mission> findByRegionId(@Param("regionId") Long regionId, Pageable pageable);
}