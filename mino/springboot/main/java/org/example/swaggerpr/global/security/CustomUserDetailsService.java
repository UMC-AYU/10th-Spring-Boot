package org.example.swaggerpr.global.security;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
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
                .orElseThrow(() -> new UsernameNotFoundException("Member not found."));

        return User.withUsername(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}
