package com.aim.umc10th.global.config.apiPayload.exception;

import com.aim.umc10th.global.config.apiPayload.code.BaseErrorCode;

public class MemberHandler extends ProjectException{
    public MemberHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
