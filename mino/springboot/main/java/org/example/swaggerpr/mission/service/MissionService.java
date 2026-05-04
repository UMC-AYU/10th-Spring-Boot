package org.example.swaggerpr.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.mission.converter.MissionConverter;
import org.example.swaggerpr.mission.dto.MissionReqDto;
import org.example.swaggerpr.mission.dto.MissionResDto;
import org.example.swaggerpr.mission.entity.mapping.MemberMission;
import org.example.swaggerpr.mission.enums.Status;
import org.example.swaggerpr.mission.exception.code.MissionErrorCode;
import org.example.swaggerpr.mission.repository.MemberMissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MemberMissionRepository memberMissionRepository;

    @Transactional(readOnly = true)
    public MissionResDto.MissionListDto getUserMissions(Long userId, String status, int page, int size) {
        Status parsedStatus = parseNullableStatus(status); // query parameter를 enum으로 반환
        Page<MemberMission> memberMissions = memberMissionRepository.findPageByMemberIdAndStatus(
                userId,
                parsedStatus,
                PageRequest.of(page, size)
        );
        return MissionConverter.toMissionListDto(memberMissions); // 결과 반환
    }

    @Transactional
    public Void completeMission(Long userId, Long missionId, MissionReqDto.CompleteMissionDto dto) {
        MemberMission memberMission = memberMissionRepository.findByMemberIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new ProjectException(MissionErrorCode.NOT_FOUND)); // 요청body의 status를 enum으로 반환
        memberMission.updateStatus(parseRequiredStatus(dto.getStatus()));
        return null;
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
            throw new ProjectException(MissionErrorCode.BAD_REQUEST);
        }
    }
}
