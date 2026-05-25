package org.example.swaggerpr.member.converter;

import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.enums.Gender;
import org.example.swaggerpr.member.enums.UserState;

public class MemberConverter {
    public static Member toMember(MemberReqDto.SignupDto dto, String encodedPassword) {
        return toMember(dto.getEmail(), encodedPassword, dto.getName(), dto.getPhone());
    }

    public static Member toMember(String email, String encodedPassword, String name, String phone) {
        return Member.builder()
                .email(email)
                // 서비스에서 BCrypt로 암호화한 비밀번호만 엔티티에 넣는다.
                .password(encodedPassword)
                .name(name)
                .phone(phone)
                .gender(Gender.NONE)
                .status(UserState.ACTIVE)
                .points(0)
                .build();
    }

    public static MemberResDto.SignupResultDto toSignupResultDto(Member member) {
        return MemberResDto.SignupResultDto.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public static MemberResDto.LoginResultDto toLoginResultDto(Member member, String accessToken) {
        return MemberResDto.LoginResultDto.builder()
                .userId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                // Swagger Authorize에는 "Bearer {accessToken}" 형식으로 입력한다.
                .tokenType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public static MemberResDto.MyPageDto toMyPageDto(Member member, long missionCount) {
        return MemberResDto.MyPageDto.builder()
                .userId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .point(member.getPoints())
                .missionCount(missionCount)
                .build();
    }
}
