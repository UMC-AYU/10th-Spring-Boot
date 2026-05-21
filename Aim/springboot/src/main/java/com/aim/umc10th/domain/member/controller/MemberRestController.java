package com.aim.umc10th.domain.member.controller;

import com.aim.umc10th.domain.member.code.MemberSuccessCode;
import com.aim.umc10th.domain.member.converter.MemberConverter;
import com.aim.umc10th.domain.member.dto.MemberRequestDTO;
import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.enums.MissionStatus;
import com.aim.umc10th.domain.member.service.MemberQueryService;
import com.aim.umc10th.domain.member.service.MemberService; // 추가
import com.aim.umc10th.domain.mission.entity.MemberMission;
import com.aim.umc10th.global.config.apiPayload.ApiResponse;
import com.aim.umc10th.global.config.apiPayload.code.BaseSuccessCode;
import com.aim.umc10th.global.config.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController //JSON 형식의 응답을 내보내기 위한 컨트롤러
@RequiredArgsConstructor //생성자 주입을 위한 어노테이션
//@RequestMapping("/auth") //URI의 접두사가 /auth로 시작하는 요청은 여기로 유도하는 어노테이션
@RequestMapping("/api")
public class MemberRestController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

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

    /*
    @GetMapping("/{memberId}/missions")
    public ApiResponse<MemberResponseDTO.MissionListDTO>getMissionList(
            @PathVariable(name = "memberId") Long memberId
    ){
        //가짜 데이터 생성 (리스트에 담을 알맹이)
        MemberResponseDTO.MissionDetailDTO missionDetailDTO = MemberResponseDTO.MissionDetailDTO.builder()
                .missionId(1L)
                .reward(1000)
                .deadline("2026-05-30")
                .missionSpec("15,000원 이상 결제 시")
                .build();

        //바구니(MissionListDTO)에 담기
        MemberResponseDTO.MissionListDTO missionListDTO = MemberResponseDTO.MissionListDTO.builder()
                .missionList(java.util.List.of(missionDetailDTO))
                .build();

        //공통 응답 규격
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missionListDTO);
    }
     */

    //미션 완료 API
     /*
    @PatchMapping("/{memberID}/member_missions/{missionId}")
    public ApiResponse<String> completeMission(
            @PathVariable(name = "memberId")Long memberId,
            @PathVariable(name = "missionId") Long missionId
    ){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "미션 완료 처리가 되었습니다.");
    }

     */

    //(마이페이지) 내 정보 조회
    @GetMapping("/me")
    public ApiResponse<MemberResponseDTO.GetInfo> getMtInfo(){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    //(마이페이지) 내 포인트 조회
    @GetMapping("/me/points")
    public ApiResponse<MemberResponseDTO.MyPointDTO> getMyPoint(){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK,
                MemberResponseDTO.MyPointDTO.builder().point(5000L).build());
    }

    //(마이페이지) 내 리뷰 목록 조회(페이징)
    @GetMapping("/me/reviews")
    public ApiResponse<MemberResponseDTO.MyReviewListDTO> getMyReviews(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ){
        //가짜 데이터 하나 생성
        MemberResponseDTO.MyReviewDetailDTO reviewDetail = MemberResponseDTO.MyReviewDetailDTO.builder()
                .reviewId(1L)
                .storeName("마라탕탕")
                .score(5.0f)
                .body("정말 맛있어요!")
                .createdAt(LocalDate.now())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MemberResponseDTO.MyReviewListDTO.builder()
                .reviewList(List.of(reviewDetail))
                .totalPage(1)
                .totalElements(1L)
                .isFirst(true)
                .isLast(true)
                .build());
    }

    @GetMapping("/{memberId}/missions")
    @Operation(summary = "진행중/진행완료 미션 목록 조회 API", description = "진행중인 미션 또는 진행완료된 미션을 조회하는 API이며, 페이징을 포함합니다. query string으로 status(CHALLENGING/COMPLETE)와 page를 주세요.")
    public ApiResponse<MemberResponseDTO.MissionListDTO> getMissionList(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page") Integer page) {

        Page<MemberMission> missionList = memberQueryService.getMissionList(memberId, status, page);
        return ApiResponse.onSuccess(MemberConverter.toMissionListDTO(missionList));
    }

    @GetMapping("/mypage/{memberId}")
    public ApiResponse<MemberResponseDTO.MyPageResultDTO> getMyPage(@PathVariable Long memberId){
        // 서비스에서 Member 엔터티 가져오기
        Member member = memberQueryService.getMyPageInfo(memberId);

        // 서비스에서 리뷰 개수 가져오기. 테이블에 리뷰 개수 관련은 없으니 직접 계산한다.
        Integer reviewCount = memberQueryService.getReviewCount(memberId);

        //컨버터를 통해 엔터티+숫자를 DTO러 변환하연 반환한다.
        return ApiResponse.onSuccess(MemberConverter.toMyPageResultDTO(member,reviewCount));
    }

}
