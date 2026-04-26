package org.example.swaggerpr.mission.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
//함수 생성의 귀찮음 덜어줌 객체지향적 관점으로 보았을 때 맞지 않은 방법이지만
//해당 객체는 비즈니스 코드가 아닌 정보전달을 위한 객체이기 때문에 이에 해당하지 않음 & private final로 선언되어있기 때문에 읽을 수 있지만 수정이 불가능
@RequiredArgsConstructor // 생성자 자동생성 및 불변성 보장
public enum MissionErrorCode implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST,
            "COMMON400_1",
            "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
            "COMMON401_1",
            "인증되지 않았습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "COMMON403_1",
            "접근이 금지되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,
            "COMMON404_1",
            "해당 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON500_1",
            "요청을 처리하지 못했습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}