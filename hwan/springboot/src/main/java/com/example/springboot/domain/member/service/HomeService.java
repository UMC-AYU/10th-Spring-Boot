package com.example.springboot.domain.member.service;

import com.example.springboot.domain.member.converter.HomeConverter;
import com.example.springboot.domain.member.dto.HomeResDTO;
import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.domain.mission.entity.Mission;
import com.example.springboot.domain.mission.repository.MissionRepository;
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

    @Transactional(readOnly = true)
    public HomeResDTO.HomeInfo getHomeInfo(Long memberId, Long regionId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Page<Mission> missionPage = missionRepository.findByRegionId(regionId, pageable);

        int completedCount = (int) member.getMissions().stream()
                .filter(um -> um.getStatus().name().equals("COMPLETE"))
                .count();

        return HomeConverter.toHomeInfo(
                missionPage,
                "지역",
                member.getPoint(),
                completedCount
        );
    }
}