package com.example.springboot.global.security.oauth;

import com.example.springboot.domain.member.entity.Member;
import com.example.springboot.domain.member.enums.SnsType;
import com.example.springboot.domain.member.repository.MemberRepository;
import com.example.springboot.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService(); // 매 요청마다 생성하지 않도록 필드로

    @Override
    @SuppressWarnings("unchecked")
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // 카카오에서 사용자 정보 가져오기

        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        String email = (String) kakaoAccount.get("email"); // 이메일 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname"); // 닉네임 추출

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save( // 신규 회원이면 자동 가입
                        Member.builder()
                                .email(email)
                                .password("")
                                .name(nickname)
                                .snsType(SnsType.KAKAO)
                                .build()
                ));

        return new AuthMember(member, oAuth2User.getAttributes()); // AuthMember로 감싸서 반환
    }
}
