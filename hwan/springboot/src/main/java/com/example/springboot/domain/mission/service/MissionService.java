package com.example.springboot.domain.mission.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.mission.converter.MissionConverter;
import com.example.springboot.domain.mission.dto.MissionReqDTO;
import com.example.springboot.domain.mission.dto.MissionResDTO;
import com.example.springboot.domain.mission.entity.mapping.UserMission;
import com.example.springboot.domain.mission.enums.MissionStatus;
import com.example.springboot.domain.mission.exception.MissionErrorCode;
import com.example.springboot.domain.mission.exception.MissionException;
import com.example.springboot.domain.mission.repository.UserMissionRepository;
import com.example.springboot.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public MissionResDTO.MissionList getMissions(Long memberId, String status, Pageable pageable) {
        MissionStatus missionStatus;
        try {
            missionStatus = MissionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MissionException(MissionErrorCode.INVALID_MISSION_STATUS);
        }

        Page<UserMission> result = userMissionRepository
                .findByMemberIdAndStatus(memberId, missionStatus, pageable);

        // 각 미션별로 리뷰 작성 여부 확인
        List<MissionResDTO.MissionItem> items = result.getContent().stream()
                .map(um -> {
                    boolean hasReview = reviewRepository.existsByMemberIdAndStoreId(
                            memberId, um.getMission().getStore().getId());
                    return MissionConverter.toMissionItem(um, hasReview);
                })
                .collect(Collectors.toList());

        return MissionConverter.toMissionList(items, result, missionStatus.name());
    }

    @Transactional
    public MissionResDTO.MissionComplete completeMission(Long userMissionId, MissionReqDTO.UpdateStatus dto) {
        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        // 이미 완료된 미션 재완료 방지
        if (userMission.getStatus() == MissionStatus.COMPLETE) {
            throw new MissionException(MissionErrorCode.MISSION_ALREADY_COMPLETED);
        }

        MissionStatus newStatus;
        try {
            newStatus = MissionStatus.valueOf(dto.status().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MissionException(MissionErrorCode.INVALID_MISSION_STATUS);
        }

        userMission.changeStatus(newStatus);

        // 포인트 지급
        if (newStatus == MissionStatus.COMPLETE) {
            Member member = userMission.getMember();
            member.addPoint(userMission.getMission().getRewardPoint());
        }

        return MissionResDTO.MissionComplete.builder()
                .userMissionId(userMission.getId())
                .status(newStatus.name())
                .rewardPoint(userMission.getMission().getRewardPoint())
                .message("미션이 완료되었습니다.")
                .build();
    }
}
