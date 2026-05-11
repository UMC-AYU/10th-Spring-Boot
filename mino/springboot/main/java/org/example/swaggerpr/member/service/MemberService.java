package org.example.swaggerpr.member.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.member.converter.MemberConverter;
import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.exception.code.MemberErrorCode;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.mission.repository.MemberMissionRepository;
import org.example.swaggerpr.mission.repository.MissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public MemberResDto.SignupResultDto signup(MemberReqDto.SignupDto dto) {
        memberRepository.findByEmail(dto.getEmail()) // 이메일 중복 확인
                .ifPresent(member -> {
                    throw new ProjectException(MemberErrorCode.BAD_REQUEST);
                });

        Member member = memberRepository.save(MemberConverter.toMember(dto)); // 객체 생성
        return MemberConverter.toSignupResultDto(member); // 결과 반환 컨버터->DTO
    }

    @Transactional(readOnly = true)
    public MemberResDto.MyPageDto getMyPage(Long userId) {
        Member member = memberRepository.findById(userId) // 회원 조회
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));
        long missionCount = memberMissionRepository.countByMemberId(userId); // 회원의 미션 수 조회
        return MemberConverter.toMyPageDto(member, missionCount); // 결과 반환
    }

    @Transactional(readOnly = true)
    public MemberResDto.HomeDto getHome(Long regionId, int page, int size) {
        // 명세서의 /users/home에는 query parameter가 없지만, "현재 선택된 지역"을 알 방법이 없어 regionId를 추가했다.
        Page<Mission> missions = missionRepository.findAvailableMissionsByRegionId(
                regionId,
                PageRequest.of(page, size)
        );
        return MemberConverter.toHomeDto(missions);
    }
}
