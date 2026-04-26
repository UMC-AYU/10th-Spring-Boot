package com.aim.umc10th.domain.store.dto;

import lombok.Getter;

public class StoreRequestDTO {
    @Getter
    public static class ReviewDTO{
        String body;
        Float score;
    }
}
