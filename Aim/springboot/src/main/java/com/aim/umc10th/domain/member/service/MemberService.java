package com.aim.umc10th.domain.member.service;

import com.aim.umc10th.domain.member.dto.MemberRequestDTO;
import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.enums.Gender;
import com.aim.umc10th.domain.member.enums.Social_type;
import com.aim.umc10th.domain.member.exception.MemberException;
import com.aim.umc10th.domain.member.repository.MemberRepository;
import com.aim.umc10th.domain.member.entity.Member; // 1. Member 엔티티 import 추가
import com.aim.umc10th.domain.member.converter.MemberConverter;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDTO.GetInfo getInfo(MemberRequestDTO.GetInfo dto){
        //DTO에서 유저 ID를 추출
        Long memberId = dto.member_id();
        //DB에서 해당 유저 ID로 데이터 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(GeneralErrorCode.NOT_FOUND));
        //컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member);
    }

    @Transactional
    public String createUser(MemberRequestDTO.joinDTO request) {
        //(8주차 미션1)
        //비밀번호는 BCrypt로 솔트처리
        String rawPassword = request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .gender(request.getGender() == 1 ? Gender.MALE:Gender.FEMALE)
                .point(0L) //회원가입 시 기본 포인트는 0

                .address(request.getAddress())
                .socialType(Social_type.None)
                .status("ACTIVE")

                .birth(java.time.LocalDate.now()) // 오늘 날짜를 임시 생일로 주입!
                .phoneNumber("010-1234-5678")
                .build();
        memberRepository.save(member);
        return "OK";
    }

    @Transactional
    public String deleteUser(){
        memberRepository.deleteByName("test");
        return "OK";
    }


    //Query Parameter
    public String singleParameter(String singleParameter)
    {
        return singleParameter;
    }

}
