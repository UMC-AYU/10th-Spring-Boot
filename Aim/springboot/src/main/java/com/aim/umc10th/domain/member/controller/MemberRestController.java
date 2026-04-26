package com.aim.umc10th.domain.member.controller;

import com.aim.umc10th.domain.member.code.MemberSuccessCode;
import com.aim.umc10th.domain.member.dto.MemberRequestDTO;
import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.service.MemberService; // 추가
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.BaseSuccessCode;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController //JSON 형식의 응답을 내보내기 위한 컨트롤러
@RequiredArgsConstructor //생성자 주입을 위한 어노테이션
//@RequestMapping("/auth") //URI의 접두사가 /auth로 시작하는 요청은 여기로 유도하는 어노테이션
@RequestMapping("/api")
public class MemberRestController {

    private final MemberService memberService;

    //예시API 마이페이지
    @PostMapping("/v1/users/me")
    public ApiResponse<MemberResponseDTO.GetInfo> getInfo(
            @org.springframework.web.bind.annotation.RequestBody MemberRequestDTO.GetInfo dto
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(dto));
    }
    //아무것도 받지 않은 경우
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    //Query Parameter
    @PostMapping("/query-parameter")
    public ApiResponse<String> exception(@RequestParam String queryParameter){
        //성공 시 사용할 코드 (BaseErrorCode 인터페이스 구현체)
        BaseSuccessCode code = GeneralSuccessCode.OK;

        //ApiResponse.onSuccess로 감싸서 반환
        return ApiResponse.onSuccess(code, memberService.singleParameter(queryParameter));
    }


    // Path Variable
    @PostMapping("/{pathVariable}")
    public String pathVariable(
            @PathVariable String pathVariable //Path Variable를 받는 어노테이션
    ){
        return memberService.singleParameter(pathVariable);
    }

    //Header
    @PostMapping("/header")
    public String header(
            @RequestHeader("test")String test //Header를 받는 어노테이션
    ){
        return memberService.singleParameter(test);
    }
}
