package com.aim.umc10th.domain.mission.repository;

import com.aim.umc10th.domain.mission.entity.Mission;
import com.aim.umc10th.domain.store.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    //특정 지역에 속한 가게들의 미션 목록을 페이징하여 가져오기
    @Query("SELECT m FROM Mission m JOIN m.store s WHERE s.region = :region")
    Page<Mission> findAllByStoreRegion(@Param("region") Region region, Pageable pageable);
}
