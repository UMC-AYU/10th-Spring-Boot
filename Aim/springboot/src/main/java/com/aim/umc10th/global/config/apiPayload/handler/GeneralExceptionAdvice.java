package com.aim.umc10th.global.config.apiPayload.handler;

import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.BaseErrorCode;
import com.aim.umc10th.global.config.apiPayload.code.GeneralErrorCode;
import com.aim.umc10th.global.config.apiPayload.exception.ProjectException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionAdvice extends ResponseEntityExceptionHandler {

    //7주차 미션3 @Valid 검증 실패 시 발생하는 예외를 가로챈다.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().forEach((org.springframework.validation.FieldError fieldError)->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return handleExceptionInternalArgs(e, headers, status, request, errors);
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(
            Exception e, HttpHeaders headers, HttpStatusCode status, WebRequest request, Map<String, String> errorArgs){

        BaseErrorCode code = GeneralErrorCode.BAD_REQUEST;

        ApiResponse<Object> body = ApiResponse.onFailure(code.getCode(), code.getMessage(), errorArgs);

        return super.handleExceptionInternal(e, body, headers, status, request);
    }

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
