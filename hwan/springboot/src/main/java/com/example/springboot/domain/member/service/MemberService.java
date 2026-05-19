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

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        // 1. 이메일 중복 확인
        if (memberRepository.existsByEmail(dto.email())) {
            throw new MemberException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // 2. 비밀번호 BCrypt 암호화
        String encodedPassword = passwordEncoder.encode(dto.password());

        // 3. Member 엔티티 생성 + 저장
        Member member = MemberConverter.toMember(dto, encodedPassword);
        memberRepository.save(member);

        // 4. 응답 DTO 반환
        return MemberConverter.toSignUpDTO(member);
    }


    @Transactional(readOnly = true)
    public MemberResDTO.MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return MemberConverter.toMyPageDTO(member);
    }
}