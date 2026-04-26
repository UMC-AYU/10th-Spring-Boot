package com.example.springboot.domain.mission.service;

import com.example.springboot.domain.mission.dto.MissionReqDTO;
import com.example.springboot.domain.mission.dto.MissionResDTO;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

    // 다음 주차에 구현 예정 (Repository 연동)
    public MissionResDTO.MissionList getMissions(String status, Integer page) {
        return null;
    }

    public MissionResDTO.MissionComplete completeMission(Long userMissionId, MissionReqDTO.UpdateStatus dto) {
        return null;
    }
}