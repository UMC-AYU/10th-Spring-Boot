package com.aim.umc10th.global.config.apiPayload;

import com.aim.umc10th.domain.review.dto.ReviewResponseDTO;
import com.aim.umc10th.global.config.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code","message","result"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final String code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private T result;


    //성공한 경우 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result){
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, "COMMON200", "요청에 성공하였습니다.", result);
    }

    //실패한 경우(result 포함)
    public static <T> ApiResponse<T> onFailure(String code, String message, T result){  //BaseErrorCode code
        return new ApiResponse<>(false, code, message, result); //false, code.getCode(), code.getMessage(), result
    }
}
