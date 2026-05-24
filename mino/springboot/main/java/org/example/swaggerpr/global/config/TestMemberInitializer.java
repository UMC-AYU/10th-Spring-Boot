package org.example.swaggerpr.global.config;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.member.converter.MemberConverter;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class TestMemberInitializer {
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password1234";
    private static final String TEST_NAME = "테스트회원";
    private static final String TEST_PHONE = "010-0000-0000";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner insertTestMember() {
        return args -> memberRepository.findByEmail(TEST_EMAIL)
                .orElseGet(() -> memberRepository.save(MemberConverter.toMember(
                        TEST_EMAIL,
                        passwordEncoder.encode(TEST_PASSWORD),
                        TEST_NAME,
                        TEST_PHONE
                )));
    }
}
