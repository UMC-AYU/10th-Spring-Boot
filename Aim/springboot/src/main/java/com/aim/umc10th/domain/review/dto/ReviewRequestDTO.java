package com.aim.umc10th.domain.review.dto;

import lombok.Getter;

public class ReviewRequestDTO {
    //사용자가 리뷰를 쓸 때 보여주는 데이터
    @Getter
    public static class CreateDTO{
        private String Body;
        private Float score;
    }
}
