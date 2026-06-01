package com.example.springboot.domain.mission.service;

import com.example.springboot.domain.mission.converter.MissionConverter;
import com.example.springboot.domain.mission.dto.MissionReqDTO;
import com.example.springboot.domain.mission.dto.MissionResDTO;
import com.example.springboot.domain.mission.entity.mapping.UserMission;
import com.example.springboot.domain.mission.enums.MissionStatus;
import com.example.springboot.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;

    @Transactional(readOnly = true)
    public MissionResDTO.MissionList getMissions(Long memberId, String status, Pageable pageable) {
        MissionStatus missionStatus = MissionStatus.valueOf(status);
        Page<UserMission> result = userMissionRepository
                .findByMemberIdAndStatus(memberId, missionStatus, pageable);
        return MissionConverter.toMissionList(result, status);
    }

    @Transactional
    public MissionResDTO.MissionComplete completeMission(Long userMissionId, MissionReqDTO.UpdateStatus dto) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new RuntimeException("미션을 찾을 수 없습니다."));
        userMission.changeStatus(MissionStatus.COMPLETE);
        return MissionResDTO.MissionComplete.builder()
                .userMissionId(userMission.getId())
                .status(MissionStatus.COMPLETE.name())
                .rewardPoint(userMission.getMission().getRewardPoint())
                .message("미션이 완료되었습니다.")
                .build();
    }
}