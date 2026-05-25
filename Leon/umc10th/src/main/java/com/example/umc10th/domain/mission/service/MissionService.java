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
import com.example.umc10th.global.enums.MissionSortType;
import com.example.umc10th.global.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.umc10th.global.sort.SortUtil.getMissionSort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService{

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    private final MissionConverter missionConverter;

    public MissionResponseDTO.Pagination<MissionResponseDTO.MissionInfo> getMissions(
            MissionSortType sort,
            Pageable pageable
    ) {

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                getMissionSort(sort)
        );

        Page<Mission> page = missionRepository.findAll(sortedPageable);

        return missionConverter.toPagination(
                page.map(missionConverter::toMissionInfo)
        );
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

    public MissionResponseDTO.Pagination<MissionResponseDTO.MissionInfo> getMissionsByRegion(
            Long regionId,
            MissionSortType sort,
            Pageable pageable
    ) {

        regionRepository.findById(regionId)
                .orElseThrow(() ->
                        new MissionException(MissionErrorCode.REGION_NOT_FOUND));

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                getMissionSort(sort)
        );

        Page<Mission> page =
                missionRepository.findByStoreRegionId(
                        regionId,
                        sortedPageable
                );

        return missionConverter.toPagination(
                page.map(missionConverter::toMissionInfo)
        );
    }

}