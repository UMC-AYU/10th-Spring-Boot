package com.aim.umc10th.domain.common.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable // 다른 엔티티에 삽입될 수 있는 클래스임을 명시
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class Address {
    private String city;
    private String district;
    private String detailAddress;
}
