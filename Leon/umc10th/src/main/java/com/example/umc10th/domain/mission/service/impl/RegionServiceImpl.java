package com.example.umc10th.domain.mission.service.impl;

import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.RegionRepository;
import com.example.umc10th.domain.mission.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    private final MissionRepository missionRepository;
    private final RegionRepository regionRepository;
    private final MissionConverter missionConverter;

    @Override
    public List<MissionResponseDTO.MissionInfo> getMissionsByRegion(Long regionId) {

        regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("지역 없음"));

        List<Mission> missions = missionRepository.findByStoreRegionId(regionId);

        return missions.stream()
                .map(missionConverter::toMissionInfo)
                .toList();
    }
}