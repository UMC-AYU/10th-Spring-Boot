package org.example.swaggerpr.mission.repository;

import org.example.swaggerpr.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query(value = """
            select m
            from Mission m
            join fetch m.store s
            join fetch s.region r
            where r.id = :regionId
            order by m.id desc
            """,
            countQuery = """
            select count(m)
            from Mission m
            join m.store s
            join s.region r
            where r.id = :regionId
            """)
    Page<Mission> findAvailableMissionsByRegionId(@Param("regionId") Long regionId, Pageable pageable);
}
