package com.aim.umc10th.domain.member.dto;

import lombok.Getter;

public class MemberRequestDTO {
    //마이페이지
    public record GetInfo(
            Long member_id //API 명세서에서 Request Body는 id만 받기로 함
    ){}
}
