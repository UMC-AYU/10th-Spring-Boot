package com.example.springboot.global.security.entity;

import com.example.springboot.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();  // 권한 없음 (이번 미션은 역할 구분 없이 로그인 여부만 확인)
    }

    @Override
    public String getPassword() {
        return member.getPassword();  // DB에 저장된 BCrypt 암호화 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return member.getEmail();  // 식별자가 email이기 떄문에 email 반환
    }
}
