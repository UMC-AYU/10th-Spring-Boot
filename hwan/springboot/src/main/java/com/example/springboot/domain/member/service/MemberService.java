package com.example.springboot.domain.member.service;

import com.example.springboot.domain.member.converter.MemberConverter;
import com.example.springboot.domain.member.dto.MemberReqDTO;
import com.example.springboot.domain.member.dto.MemberResDTO;
import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResDTO.SignUp signUp(MemberReqDTO.SignUp dto) {
        return null;
    }

    @Transactional(readOnly = true)
    public MemberResDTO.MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return MemberConverter.toMyPageDTO(member);
    }
}