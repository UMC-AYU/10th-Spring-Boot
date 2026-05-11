package org.example.swaggerpr.mission.repository;

import org.example.swaggerpr.mission.entity.mapping.MemberMission;
import org.example.swaggerpr.mission.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Query(value = """
            select mm
            from MemberMission mm
            join fetch mm.mission m
            join fetch m.store s
            where mm.member.id = :memberId
              and (:status is null or mm.status = :status)
            order by mm.id desc
            """,
            countQuery = """
                    select count(mm)
                    from MemberMission mm
                    where mm.member.id = :memberId
                      and (:status is null or mm.status = :status)
                    """)
    Page<MemberMission> findPageByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
            select mm
            from MemberMission mm
            join fetch mm.mission m
            join fetch mm.member u
            where u.id = :memberId
              and m.id = :missionId
            """)
    Optional<MemberMission> findByMemberIdAndMissionId(
            @Param("memberId") Long memberId,
            @Param("missionId") Long missionId
    );

    long countByMemberId(Long memberId);
}
