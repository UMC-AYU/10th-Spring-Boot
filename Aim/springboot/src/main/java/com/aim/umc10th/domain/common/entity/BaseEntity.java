package com.aim.umc10th.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //맴버 클래스들이 이 클래스를 상속할 경우 필드들도 컬럼으로 인식하게 해줌
@EntityListeners(AuditingEntityListener.class) //엔티티의 변화를 감지하여 시간을 자동으로 입력
@Getter
public abstract class BaseEntity {

    @CreatedDate //생성 시각 자동 저장
    @Column(name = "created_at", updatable = false) // 생성일은 수정 불가능하게 설정
    private LocalDateTime createdAt;

    @LastModifiedDate //마지막 수정 시각 자동 저장
    @Column(name = "upadted_at")
    private LocalDateTime updatedAt;
}
