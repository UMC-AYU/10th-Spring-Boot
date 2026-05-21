package com.aim.umc10th.global.config.apiPayload.handler;

import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.BaseErrorCode;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.global.config.apiPayload.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionAdvice{
    //프로젝트에서 발생한 예외처리
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(
            ProjectException e
    ){
        BaseErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), null));
    }

    //그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex
    ){

        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(
                        code.getCode(),
                        code.getMessage(),
                        ex.getMessage()
                )
                );
    }

}
