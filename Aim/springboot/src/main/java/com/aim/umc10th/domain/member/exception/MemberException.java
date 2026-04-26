package com.aim.umc10th.domain.member.exception;

import com.aim.umc10th.global.config.apiPayload.code.BaseErrorCode;
import com.aim.umc10th.global.config.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode errorCode) {super(errorCode);}
}
