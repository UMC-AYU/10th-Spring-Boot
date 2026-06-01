package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.global.enums.MissionStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResponseDTO {

    @Getter
    @Builder
    public static class MissionInfo {
        private Long missionId;
        private String title;
        private MissionStatus status;
        private String store;
    }

    @Getter
    @Builder
    public static class Pagination<T> {
        private List<T> data;
        private Integer pageNumber;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;
        private Boolean isLast;
    }

}