package org.example.swaggerpr.global.apiPayload.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.BaseErrorCode;

@Getter
//함수 생성의 귀찮음 덜어줌 객체지향적 관점으로 보았을 때 맞지 않은 방법이지만
//해당 객체는 비즈니스 코드가 아닌 정보전달을 위한 객체이기 때문에 이에 해당하지 않음 & private final로 선언되어있기 때문에 읽을 수 있지만 수정이 불가능
@RequiredArgsConstructor // 생성자 자동생성 및 불변성 보장
public class ProjectException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
