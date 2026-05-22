package org.example.swaggerpr.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.GeneralErrorCode;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.mission.converter.MissionConverter;
import org.example.swaggerpr.mission.dto.MissionReqDto;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.mission.entity.mapping.MemberMission;
import org.example.swaggerpr.mission.enums.Status;
import org.example.swaggerpr.mission.exception.code.MissionErrorCode;
import org.example.swaggerpr.mission.repository.MemberMissionRepository;
import org.example.swaggerpr.mission.repository.MissionRepository;
import org.example.swaggerpr.store.entity.Region;
import org.example.swaggerpr.store.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public MissionResDto.MissionListDto getUserMissions(Long userId, String status, int page, int size) {
        Status parsedStatus = parseNullableStatus(status);
        Page<MemberMission> memberMissions = memberMissionRepository.findPageByMemberIdAndStatus(
                userId,
                parsedStatus,
                PageRequest.of(page, size)
        );
        return MissionConverter.toMissionListDto(memberMissions);
    }

    @Transactional(readOnly = true)
    public MissionResDto.MissionListDto getChallengingMissions(MissionReqDto.ChallengingMissionSearchDto dto) {
        Page<MemberMission> memberMissions = memberMissionRepository.findPageByMemberIdAndStatus(
                dto.getUserId(),
                Status.CHALLENGING,
                PageRequest.of(dto.getPage(), dto.getSize())
        );
        return MissionConverter.toMissionListDto(memberMissions);
    }

    @Transactional(readOnly = true)
    public MissionResDto.NearbyMissionListDto getNearbyMissions(Long userId, Long regionId, int page, int size) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        Page<Mission> missions = missionRepository.findAvailableMissionsByRegionId(
                userId,
                regionId,
                LocalDate.now(),
                PageRequest.of(page, size)
        );
        return MissionConverter.toNearbyMissionListDto(region.getName(), missions);
    }

    @Transactional
    public void completeMission(Long userId, Long missionId, MissionReqDto.CompleteMissionDto dto) {
        MemberMission memberMission = memberMissionRepository.findByMemberIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new ProjectException(MissionErrorCode.NOT_FOUND));
        Status nextStatus = parseRequiredStatus(dto.getStatus());

        if (memberMission.getStatus() != Status.CHALLENGING || nextStatus != Status.COMPLETE) {
            throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }

        memberMission.updateStatus(nextStatus);
    }

    private Status parseNullableStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        return parseRequiredStatus(status);
    }

    private Status parseRequiredStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ProjectException(GeneralErrorCode.BAD_REQUEST);
        }
    }
}
