package com.example.umc10th.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MemberResponseDTO {

    @Getter
    @Builder
    public static class GetMyInfo {
        private Long memberId;
        private String email;
        private String name;
    }

    @Getter
    @Builder
    public static class Login {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class MyMission {
        private Long missionId;
        private String status;
    }

    @Getter
    @Builder
    public static class MyReview {
        private Long reviewId;
        private String content;
        private Integer rating;
    }
}