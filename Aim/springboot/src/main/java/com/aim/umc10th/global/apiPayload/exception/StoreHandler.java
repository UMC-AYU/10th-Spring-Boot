package com.aim.umc10th.global.apiPayload.exception;

import com.aim.umc10th.global.apiPayload.code.BaseErrorCode;

public class StoreHandler extends ProjectException{
    public StoreHandler(BaseErrorCode code){
        super (code);
    }
}
