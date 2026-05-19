package com.example.springboot.global.security.service;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.exception.MemberErrorCode;
import com.example.springboot.domain.member.exception.MemberException;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));
        return new AuthMember(member);
    }
}