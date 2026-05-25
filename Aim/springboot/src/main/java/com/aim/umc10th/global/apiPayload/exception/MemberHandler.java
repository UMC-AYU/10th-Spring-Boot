package com.aim.umc10th.global.apiPayload.exception;

import com.aim.umc10th.global.apiPayload.code.BaseErrorCode;

public class MemberHandler extends ProjectException{
    public MemberHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
