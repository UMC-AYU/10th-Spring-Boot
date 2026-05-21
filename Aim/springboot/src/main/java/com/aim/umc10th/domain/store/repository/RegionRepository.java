package com.aim.umc10th.domain.store.repository;

import com.aim.umc10th.domain.store.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    // 지역 이름으로 지역 엔터티를 찾아오는 메소드이다.
    //나중에 서비스에서 ex."안암동"을 넘겨주면 이 메서드가 DB에서 해당 Region 객체를 찾아준다!
    Optional<Region> findByName(String name);
}
