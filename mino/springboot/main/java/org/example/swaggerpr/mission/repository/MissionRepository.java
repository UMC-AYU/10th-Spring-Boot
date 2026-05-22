package org.example.swaggerpr.mission.repository;

import org.example.swaggerpr.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query(value = """
        select m
        from Mission m
        join fetch m.store s
        join fetch s.region r
        where r.id = :regionId
          and m.deadline >= :today
          and not exists (
              select 1
              from MemberMission mm
              where mm.mission = m
                and mm.member.id = :userId
          )
        order by m.id desc
        """,
            countQuery = """
        select count(m)
        from Mission m
        join m.store s
        join s.region r
        where r.id = :regionId
          and m.deadline >= :today
          and not exists (
              select 1
              from MemberMission mm
              where mm.mission = m
                and mm.member.id = :userId
          )
        """)
    Page<Mission> findAvailableMissionsByRegionId(
            @Param("userId") Long userId,
            @Param("regionId") Long regionId,
            @Param("today") LocalDate today,
            Pageable pageable
    );
}
