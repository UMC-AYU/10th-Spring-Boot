package com.example.springboot.domain.review.converter;

import com.example.springboot.domain.review.dto.ReviewResDTO;
import com.example.springboot.domain.review.entity.Review;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewConverter {

    public static ReviewResDTO.CreateResult toCreateDTO(Review review) {
        return ReviewResDTO.CreateResult.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .score(review.getScore())
                .content(review.getContent())
                .createdAt(review.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    public static ReviewResDTO.ReviewItem toReviewItem(Review review) {
        return ReviewResDTO.ReviewItem.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .score(review.getScore())
                .storeName(review.getStore().getName())
                .createdAt(review.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }

    public static ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItem> toCursorPagination(
            Slice<Review> slice, String query) {

        List<ReviewResDTO.ReviewItem> data = slice.getContent().stream()
                .map(ReviewConverter::toReviewItem)
                .toList();

        String nextCursor = null;
        if (slice.hasNext() && !slice.getContent().isEmpty()) {
            Review last = slice.getContent().get(slice.getContent().size() - 1);
            nextCursor = "rating".equals(query)
                    ? "rating:" + last.getScore() + ":" + last.getId()
                    : "id:" + last.getId();
        }

        return ReviewResDTO.CursorPagination.<ReviewResDTO.ReviewItem>builder()
                .data(data)
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .pageSize(slice.getSize())
                .build();
    }
}