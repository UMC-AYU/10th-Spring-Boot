package com.aim.umc10th.domain.store.service;

import com.aim.umc10th.domain.mission.entity.Mission;
import com.aim.umc10th.domain.mission.repository.MissionRepository;
import com.aim.umc10th.domain.store.entity.Region;
import com.aim.umc10th.domain.store.repository.RegionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService{

    private final RegionRepository regionRepository;
    private final MissionRepository missionRepository;

    @Override
    public Page<Mission> getMissionListByRegion(String regionName, Integer page){


        // 전달받은 지역 이름으로 DB에서 Region 객체 찾기
        // 만약 해당 이름의 지역이 없으면 예외를 발생.
        Region region = regionRepository.findByName(regionName)
                .orElseThrow(()-> new RuntimeException("해당 지역을 찾을 수 없습니다."));

        // MissionRepository에 만든 쿼리를 호출
        //PageRequest.of를 통해 페이징 설정을 넘긴다. (한 페이지당 10개씩)
        return missionRepository.findAllByStoreRegion(region, PageRequest.of(page, 10));
    }
}
