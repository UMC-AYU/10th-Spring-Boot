package com.example.springboot.domain.store.exception;

import com.example.springboot.global.apiPayload.code.BaseErrorCode;
import com.example.springboot.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {

    public StoreException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
