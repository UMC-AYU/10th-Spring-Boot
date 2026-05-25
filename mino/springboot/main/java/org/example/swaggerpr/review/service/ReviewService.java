package org.example.swaggerpr.review.service;

import lombok.RequiredArgsConstructor;
import org.example.swaggerpr.global.apiPayload.exception.ProjectException;
import org.example.swaggerpr.member.entity.Member;
import org.example.swaggerpr.member.exception.code.MemberErrorCode;
import org.example.swaggerpr.member.repository.MemberRepository;
import org.example.swaggerpr.mission.entity.Mission;
import org.example.swaggerpr.mission.exception.code.MissionErrorCode;
import org.example.swaggerpr.mission.repository.MissionRepository;
import org.example.swaggerpr.review.converter.ReviewConverter;
import org.example.swaggerpr.review.dto.ReviewReqDto;
import org.example.swaggerpr.review.dto.ReviewResDto;
import org.example.swaggerpr.review.entity.Review;
import org.example.swaggerpr.review.repository.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public ReviewResDto.CreateReviewResultDto createReview(Long userId, Long missionId, ReviewReqDto.CreateReviewDto dto) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ProjectException(MissionErrorCode.NOT_FOUND));

        Review review = reviewRepository.save(Review.builder()
                .member(member)
                .store(mission.getStore())
                .score(dto.getScore().floatValue())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build());

        return ReviewConverter.toCreateReviewResultDto(review, missionId);
    }

    @Transactional(readOnly = true)
    public ReviewResDto.MyReviewCursorListDto getMyReviews(ReviewReqDto.MyReviewCursorDto dto) {
        memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ProjectException(MemberErrorCode.NOT_FOUND));

        int requestedSize = dto.getSize();
        List<Review> reviews = switch (dto.getSortBy()) {
            case SCORE -> reviewRepository.findMyReviewsOrderByScore(
                    dto.getUserId(),
                    dto.getCursorScore(),
                    dto.getCursorId(),
                    PageRequest.of(0, requestedSize + 1)
            );
            case ID -> reviewRepository.findMyReviewsOrderById(
                    dto.getUserId(),
                    dto.getCursorId(),
                    PageRequest.of(0, requestedSize + 1)
            );
        };

        boolean hasNext = reviews.size() > requestedSize;
        List<Review> pageReviews = hasNext ? reviews.subList(0, requestedSize) : reviews;
        Review lastReview = pageReviews.isEmpty() ? null : pageReviews.get(pageReviews.size() - 1);

        return ReviewResDto.MyReviewCursorListDto.builder()
                .reviews(pageReviews.stream()
                        .map(ReviewConverter::toMyReviewPreviewDto)
                        .toList())
                .nextCursorId(lastReview == null ? null : lastReview.getId())
                .nextCursorScore(lastReview == null ? null : lastReview.getScore())
                .size(pageReviews.size())
                .hasNext(hasNext)
                .build();
    }
}
