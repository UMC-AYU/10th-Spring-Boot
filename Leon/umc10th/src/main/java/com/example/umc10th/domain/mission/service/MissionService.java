package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.store.repository.RegionRepository;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService{

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    private final MissionConverter missionConverter;

    public List<MissionResponseDTO.MissionInfo> getMissions(MissionStatus status) {

        List<Mission> missions = missionRepository.findAll();

        return missions.stream()
                .map(missionConverter::toMissionInfo)
                .toList();
    }

    @Transactional
    public void startMission(Long memberId, Long missionId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        memberMissionRepository.findByMemberIdAndMissionId(memberId, missionId)
                .ifPresent(mm -> {
                    throw new MissionException(MissionErrorCode.MISSION_ALREADY_STARTED);
                });

        MemberMission memberMission = missionConverter.toMemberMission(member, mission);

        memberMissionRepository.save(memberMission);
    }

    @Transactional
    public void completeMission(Long memberId, Long missionId) {

        MemberMission memberMission = memberMissionRepository
                .findByMemberIdAndMissionId(memberId, missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_IN_PROGRESS));

        if (memberMission.getStatus() == MissionStatus.COMPLETED) {
            throw new MissionException(MissionErrorCode.MISSION_ALREADY_COMPLETED);
        }

        memberMission.complete();
    }

    public List<MissionResponseDTO.MissionInfo> getMissionsByRegion(Long regionId) {

        regionRepository.findById(regionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.REGION_NOT_FOUND));

        List<Mission> missions = missionRepository.findByStoreRegionId(regionId);

        return missions.stream()
                .map(missionConverter::toMissionInfo)
                .toList();
    }
}