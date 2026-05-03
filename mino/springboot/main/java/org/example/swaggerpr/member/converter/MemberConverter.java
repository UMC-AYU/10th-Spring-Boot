package org.example.swaggerpr.member.converter;

import org.example.swaggerpr.member.dto.MemberReqDto;
import org.example.swaggerpr.member.dto.MemberResDto;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.enums.Gender;
import org.example.swaggerpr.member.enums.UserState;
import org.example.swaggerpr.mission.entity.Mission;
import org.springframework.data.domain.Page;

public class MemberConverter {
    public static Member toMember(MemberReqDto.SignupDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword()) // 추후 암호화 추가 필요
                .name(dto.getName())
                .phone(dto.getPhone())
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

    public static MemberResDto.HomeDto toHomeDto(Page<Mission> missions) {
        return MemberResDto.HomeDto.builder()
                // 첫 화면은 지역을 기준으로 미션을 보여주므로, 조회 결과의 첫 미션에서 지역명을 가져온다.
                .regionName(missions.hasContent() ? missions.getContent().getFirst().getStore().getRegion().getName() : null)
                .missions(missions.getContent().stream()
                        .map(mission -> MemberResDto.HomeMissionDto.builder()
                                .missionId(mission.getId())
                                .storeName(mission.getStore().getName())
                                .content(mission.getContent())
                                .rewardPoint(mission.getRewardPoint())
                                .build())
                        .toList())
                .page(missions.getNumber())
                .size(missions.getSize())
                .totalElements(missions.getTotalElements())
                .totalPages(missions.getTotalPages())
                .build();
    }
}
