package org.example.swaggerpr.member.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.GeneralErrorCode;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.global.security.AuthMember;
import org.example.swaggerpr.global.security.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberResDto.SignupResultDto signup(MemberReqDto.SignupDto dto) {
        // 동일 email로 중복 가입하는 것을 먼저 막는다.
        memberRepository.findByEmail(dto.getEmail())
                .ifPresent(member -> {
                    throw new ProjectException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
                });

        // 비밀번호는 BCrypt로 암호화한 값만 저장한다.
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = memberRepository.save(MemberConverter.toMember(dto, encodedPassword));
        return MemberConverter.toSignupResultDto(member);
    }

    @Transactional(readOnly = true)
    public MemberResDto.LoginResultDto login(MemberReqDto.LoginDto dto) {
        // 로그인 실패 사유를 자세히 노출하지 않기 위해 email/비밀번호 오류 모두 401로 처리한다.
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }

        // 인증에 성공한 회원 정보를 AuthMember로 감싸 JWT를 발급한다.
        String accessToken = jwtUtil.createAccessToken(new AuthMember(member));
        return MemberConverter.toLoginResultDto(member, accessToken);
    }

    @Transactional(readOnly = true)
    public MemberResDto.MyPageDto getMyPage(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));
        long missionCount = memberMissionRepository.countByMemberId(userId);
        return MemberConverter.toMyPageDto(member, missionCount);
    }

    @Transactional(readOnly = true)
    public MemberResDto.MyPageDto getMyPage(Member member) {
        // JWT 필터에서 이미 조회한 회원 객체를 재사용해 현재 로그인한 사용자의 마이페이지를 만든다.
        long missionCount = memberMissionRepository.countByMemberId(member.getId());
        return MemberConverter.toMyPageDto(member, missionCount);
    }
}
