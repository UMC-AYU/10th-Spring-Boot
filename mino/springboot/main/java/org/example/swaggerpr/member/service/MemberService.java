package org.example.swaggerpr.member.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.member.converter.MemberConverter;
import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.exception.code.MemberErrorCode;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.example.swaggerpr.mission.repository.MemberMissionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResDto.SignupResultDto signup(MemberReqDto.SignupDto dto) {
        memberRepository.findByEmail(dto.getEmail())
                .ifPresent(member -> {
                    throw new ProjectException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
                });

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = memberRepository.save(MemberConverter.toMember(dto, encodedPassword));
        return MemberConverter.toSignupResultDto(member);
    }

    @Transactional(readOnly = true)
    public MemberResDto.MyPageDto getMyPage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));
        long missionCount = memberMissionRepository.countByMemberId(userId);
        return MemberConverter.toMyPageDto(member, missionCount);
    }
}
