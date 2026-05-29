package com.example.springboot.domain.member.service;

import com.example.springboot.domain.member.converter.MemberConverter;
import com.example.springboot.domain.member.dto.MemberReqDTO;
import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.exception.MemberErrorCode;
import com.example.springboot.domain.member.exception.MemberException;
import com.example.springboot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springboot.global.security.jwt.JwtUtil;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(dto.email())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto, encodedPassword);
        memberRepository.save(member);
        return MemberConverter.toSignUpDTO(member);
    }


    @Transactional(readOnly = true)
    public MemberResDTO.MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));
        return MemberConverter.toMyPageDTO(member);
    }

    @Transactional(readOnly = true)
    public MemberResDTO.Login login(MemberReqDTO.Login dto) {
        Member member = memberRepository.findByEmail(dto.email()) // 이메일로 회원 조회
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(dto.password(), member.getPassword())) { // 비밀번호 검증
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
        String accessToken = jwtUtil.createAccessToken(member.getEmail()); // JWT 발급
        return MemberConverter.toLoginDTO(member, accessToken);
    }
}