package com.example.springboot.domain.mission.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.exception.MemberErrorCode;
import com.example.springboot.domain.member.exception.MemberException;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.domain.mission.converter.HomeConverter;
import com.example.springboot.domain.mission.dto.HomeResDTO;
import com.example.springboot.domain.mission.entity.Mission;
import com.example.springboot.domain.mission.enums.MissionStatus;
import com.example.springboot.domain.mission.repository.MissionRepository;
import com.example.springboot.domain.mission.repository.UserMissionRepository;
import com.example.springboot.domain.store.entity.Region;
import com.example.springboot.domain.store.exception.StoreErrorCode;
import com.example.springboot.domain.store.exception.StoreException;
import com.example.springboot.domain.store.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final UserMissionRepository userMissionRepository;

    @Transactional(readOnly = true)
    public HomeResDTO.HomeInfo getHomeInfo(Long memberId, Long regionId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.REGION_NOT_FOUND));

        Page<Mission> missionPage = missionRepository.findByRegionId(regionId, pageable);

        // DB에서 완료 미션 수 조회
        int completedCount = (int) userMissionRepository.countByMemberIdAndStatus(memberId, MissionStatus.COMPLETE);

        return HomeConverter.toHomeInfo(missionPage, region.getName(), member.getPoint(), completedCount);
    }
}
