package com.aim.umc10th.domain.member.converter;

import com.aim.umc10th.domain.member.dto.MemberResponseDTO;
import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import com.aim.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    public static MemberResponseDTO.GetInfo toGetInfo(
            Member member
    ){
        return MemberResponseDTO.GetInfo.builder()
                .email((member.getEmail()))
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    public static MemberResponseDTO.MissionListDTO toMissionListDTO(Page<MemberMission> missionList) {

        List<MemberResponseDTO.MissionDetailDTO> missionDetailDTOList = missionList.stream()
                .map(memberMission -> MemberResponseDTO.MissionDetailDTO.builder()
                        .missionId(memberMission.getMission().getId())
                        .storeName(memberMission.getMission().getStore().getName())
                        .reward(memberMission.getMission().getReward())
                        .missionSpec(memberMission.getMission().getMissionSpec())
                        .status(memberMission.getStatus().toString())
                        .createdAt(memberMission.getCreatedAt().toLocalDate())
                        .build()
                ).collect(Collectors.toList());

        return MemberResponseDTO.MissionListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionDetailDTOList.size())
                .missionList(missionDetailDTOList)
                .build();
    }

    public static MemberResponseDTO.MyPageResultDTO toMyPageResultDTO(Member member, Integer reviewCount){
        return MemberResponseDTO.MyPageResultDTO.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phone_number(member.getPhoneNumber())
                .point(member.getPoint())
                .reviewCount(reviewCount)
                .build();
    }

    //7주차 미션1
    public static MemberResponseDTO.MyMissionDTO toMyMissionDTO(MemberMission memberMission){
        return MemberResponseDTO.MyMissionDTO.builder()
                .memberMissionId(memberMission.getId())
                .storeName(memberMission.getMission().getStore().getName()) // 연관된 가게 이름
                .missionSpec(memberMission.getMission().getMissionSpec()) //미션내용
                .reward(memberMission.getMission().getReward()) //포인트
                .status(memberMission.getStatus().toString()) //CHALLENGING
                .build();

    }

    public static MemberResponseDTO.MyMissionListDTO toMyMissionListDTO(Page<MemberMission> missionList){
        List<MemberResponseDTO.MyMissionDTO> myMissionDTOList = missionList.getContent().stream()
                .map(MemberConverter::toMyMissionDTO)
                .collect(Collectors.toList());

        return MemberResponseDTO.MyMissionListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(myMissionDTOList.size())
                .missionList(myMissionDTOList)
                .build();
    }

    //7주차 미션2 (Slice 객체를 받아서 다음 조회를 위한 커서(ID)를 계산해서 넘겨주는게 핵심이다..!!)
    public static MemberResponseDTO.MyReviewDetailDTO toMyReviewDTO(Review review){
        return MemberResponseDTO.MyReviewDetailDTO.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName()) //가게 이름 가지고오기
                .score(review.getScore())
                .body(review.getBody())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();

    }

    public static MemberResponseDTO.MyReviewListDTO toMyReviewListDTO(Slice<Review> reviewList){
        List<MemberResponseDTO.MyReviewDetailDTO> myReviewDTOList = reviewList.getContent().stream()
                .map(MemberConverter::toMyReviewDTO)
                .collect(Collectors.toList());

        //다음 커서 ID 계산: 리스트가 비어있지 않으면 마지막 요소의 ID 전달한다.
        Long nextCursor = reviewList.hasNext()
                ? myReviewDTOList.get(myReviewDTOList.size() -1).getReviewId()
                : null;

        return MemberResponseDTO.MyReviewListDTO.builder()
                .reviewList(myReviewDTOList)
                .listSize(myReviewDTOList.size())
                .hasNext(reviewList.hasNext()) //slice가 제공하는 다음 페이지 존재 여부
                .nextCursor(nextCursor) //다음 조회를 위한 Last ID
                .build();
    }
}
