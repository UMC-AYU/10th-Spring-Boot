package com.aim.umc10th.domain.store.service;

import ch.qos.logback.core.status.ErrorStatus;
import com.aim.umc10th.domain.mission.converter.MissionConverter;
import com.aim.umc10th.domain.mission.dto.MissionRequestDTO;
import com.aim.umc10th.domain.mission.entity.Mission;
import com.aim.umc10th.domain.mission.repository.MissionRepository;
import com.aim.umc10th.domain.store.entity.Store;
import com.aim.umc10th.domain.store.repository.StoreRepository;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.global.config.apiPayload.exception.StoreHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService{

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    @Override
    public Mission createMission(Long storeId, MissionRequestDTO.MissionCreateDTO request){

        //Converter를 이용해 DTO를 엔티티로 변환
        Mission mission = MissionConverter.toMission(request);

        //해당 가게가 존재하는지 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new StoreHandler(GeneralErrorCode.STORE_NOT_FOUND));
        // 미션과 가게 연관관계 설정
        mission.setStore(store);

        return missionRepository.save(mission);

    }
}
