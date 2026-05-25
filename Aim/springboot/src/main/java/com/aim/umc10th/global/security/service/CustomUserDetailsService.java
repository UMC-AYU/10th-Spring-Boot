package com.aim.umc10th.global.security.service;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.repository.MemberRepository;
import com.aim.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자가 로그인 폼에 아이디/비번을 치고 들어왔을 때, Security가 우리 프로젝트의 DB에서
 * 진짜 회원이 맞는지 조회를 하는 실무를 담당
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // DB에 직접 접근하기 위한 영속성 계층의 레포지토리 주입받음
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {
        //1. 주입받은 레포지토리를 사용하여, 사용자가 입력한 이메일(username)로 DB에서 회원을 조회한다.
        Member member = memberRepository.findByEmail(username)
                //2. 만약 DB에 해당 이메일을 가진 회원이 존재하지 않다면,
                // GeneralErrorCode의 MEMBER_NOT_FOUND 에러를 실어 예외 처리
                .orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));
        // 3. DB에서 회원을 성공적으로 찾아왔다면, 스프링 Security가 읽을 수 있도록
        // AuthMember에 담아 반환한다.
        return new AuthMember(member);
    }
}
