package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionResponseDTO;

import java.util.List;

public interface MissionService {

    List<MissionResponseDTO.MissionInfo> getMissions(String status);

    void startMission(Long memberId, Long missionId);

    void completeMission(Long memberId, Long missionId);
}