package com.aim.umc10th.domain.store.controller;

import com.aim.umc10th.domain.store.dto.StoreRequestDTO;
import com.aim.umc10th.domain.store.dto.StoreResponseDTO;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/stores")
public class StoreRestController {

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<StoreResponseDTO.CreateReviewResultDTO> createReview(
            @PathVariable(name = "storeId") Long storeId,
            @RequestBody StoreRequestDTO.ReviewDTO request
    ){
        //이번 미션에는 서버가 없으니 가짜 데이터를 보냈다.
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,  StoreResponseDTO.CreateReviewResultDTO.builder().reviewId(1L)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
