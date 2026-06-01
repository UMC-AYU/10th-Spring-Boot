package com.aim.umc10th.global.config.apiPayload.exception;

import com.aim.umc10th.global.config.apiPayload.code.BaseErrorCode;

public class StoreHandler extends ProjectException{
    public StoreHandler(BaseErrorCode code){
        super (code);
    }
}
