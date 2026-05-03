package com.example.umc10th.domain.member.service.impl;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberRequestDTO;
import com.example.umc10th.domain.member.dto.MemberResponseDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.member.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Override
    @Transactional
    public MemberResponseDTO.GetMyInfo signup(MemberRequestDTO.Signup dto) {

        Member member = memberConverter.toMember(dto);

        Member savedMember = memberRepository.save(member);

        return memberConverter.toGetMyInfo(savedMember);
    }

    @Override
    public MemberResponseDTO.Login login(MemberRequestDTO.Login dto) {

        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        return memberConverter.toLogin(member);
    }
}