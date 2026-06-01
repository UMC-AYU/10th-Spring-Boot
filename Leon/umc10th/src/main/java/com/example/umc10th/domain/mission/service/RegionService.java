package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionResponseDTO;

import java.util.List;

public interface RegionService {

    List<MissionResponseDTO.MissionInfo> getMissionsByRegion(Long regionId);
}