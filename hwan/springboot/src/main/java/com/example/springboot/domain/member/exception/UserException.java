package com.example.springboot.domain.member.exception;

import com.example.springboot.global.apiPayload.code.BaseErrorCode;
import com.example.springboot.global.apiPayload.exception.ProjectException;

public class UserException extends ProjectException {
    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}