package com.example.umc10th.domain.mission.service.impl;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResponseDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
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
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    private final MissionConverter missionConverter;

    @Override
    public List<MissionResponseDTO.MissionInfo> getMissions(String status) {

        List<Mission> missions = missionRepository.findAll();

        return missions.stream()
                .map(missionConverter::toMissionInfo)
                .toList();
    }

    @Override
    @Transactional
    public void startMission(Long memberId, Long missionId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("미션 없음"));

        memberMissionRepository.findByMemberIdAndMissionId(memberId, missionId)
                .ifPresent(mm -> {
                    throw new RuntimeException("이미 시작한 미션입니다.");
                });

        MemberMission memberMission = missionConverter.toMemberMission(member, mission);

        memberMissionRepository.save(memberMission);
    }

    @Override
    @Transactional
    public void completeMission(Long memberId, Long missionId) {

        MemberMission memberMission = memberMissionRepository
                .findByMemberIdAndMissionId(memberId, missionId)
                .orElseThrow(() -> new RuntimeException("진행중인 미션이 없습니다."));

        if (memberMission.getStatus() == MissionStatus.COMPLETED) {
            throw new RuntimeException("이미 완료된 미션입니다.");
        }

        memberMission.complete();

    }
}