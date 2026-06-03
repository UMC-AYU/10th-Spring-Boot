package com.example.umc10th.global.sort;

import com.example.umc10th.global.enums.MissionSortType;
import com.example.umc10th.global.enums.ReviewSortType;
import org.springframework.data.domain.Sort;

public class SortUtil {

    private SortUtil() {
    }

    public static Sort getMissionSort(MissionSortType sort) {

        return switch (sort) {

            case LATEST ->
                    Sort.by(
                            Sort.Order.desc("createdAt"),
                            Sort.Order.desc("id")
                    );

            case OLDEST ->
                    Sort.by(
                            Sort.Order.asc("createdAt"),
                            Sort.Order.asc("id")
                    );

            case REWARD_HIGH ->
                    Sort.by(
                            Sort.Order.desc("reward"),
                            Sort.Order.desc("id")
                    );

            case REWARD_LOW ->
                    Sort.by(
                            Sort.Order.asc("reward"),
                            Sort.Order.asc("id")
                    );

            case DEADLINE_SOON ->
                    Sort.by(
                            Sort.Order.asc("deadline"),
                            Sort.Order.asc("id")
                    );
        };
    }

    public static Sort getReviewSort(ReviewSortType sort) {

        return switch (sort) {

            case LATEST ->
                    Sort.by(
                            Sort.Order.desc("createdAt"),
                            Sort.Order.desc("id")
                    );

            case OLDEST ->
                    Sort.by(
                            Sort.Order.asc("createdAt"),
                            Sort.Order.asc("id")
                    );

            case RATING_HIGH ->
                    Sort.by(
                            Sort.Order.desc("rating"),
                            Sort.Order.desc("id")
                    );

            case RATING_LOW ->
                    Sort.by(
                            Sort.Order.asc("rating"),
                            Sort.Order.asc("id")
                    );
        };
    }
}