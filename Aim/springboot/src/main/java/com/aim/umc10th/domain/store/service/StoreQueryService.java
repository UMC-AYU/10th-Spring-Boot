package com.aim.umc10th.domain.store.service;

import com.aim.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

public interface StoreQueryService {

    //특정 지역의 이름과 페이지 번호를 받아 해당 지역 가게들의 미션 목록을 반환한다.
    Page<Mission> getMissionListByRegion(String regionName, Integer page);
}
