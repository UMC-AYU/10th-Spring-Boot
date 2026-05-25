package com.aim.umc10th.domain.store.service;

import com.aim.umc10th.domain.mission.entity.Mission;
import com.aim.umc10th.domain.mission.repository.MissionRepository;
import com.aim.umc10th.domain.store.entity.Store;
import com.aim.umc10th.domain.store.repository.RegionRepository;
import com.aim.umc10th.domain.store.repository.StoreRepository;
import com.aim.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.global.apiPayload.exception.StoreHandler;
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
    private final StoreRepository storeRepository;

    /*
    @Override
    public Page<Mission> getMissionListByRegion(String regionName, Integer page){
        return missionRepository.findAllByStoreRegionName(regionName, PageRequest.of(page,10));
    }
     */

    @Override
    public Page<Mission> getMissionListByStore(Long storeId, Integer page){

        // 가게가 존재하는지 먼저 확인
        // 만약 해당 가게가 없으면 예외를 발생.
        Store store= storeRepository.findById(storeId)
                .orElseThrow(()-> new StoreHandler(GeneralErrorCode.STORE_NOT_FOUND));

        // MissionRepository에 만든 쿼리를 호출
        //PageRequest.of를 통해 페이징 설정을 넘긴다. (한 페이지당 10개씩)
        return missionRepository.findAllByStore(store, PageRequest.of(page, 10));
    }
}
