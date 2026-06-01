package com.aim.umc10th.domain.store.service;

import com.aim.umc10th.domain.mission.dto.MissionRequestDTO;
import com.aim.umc10th.domain.mission.entity.Mission;

public interface StoreCommandService {
    Mission createMission(Long storeId, MissionRequestDTO.MissionCreateDTO request);
}
