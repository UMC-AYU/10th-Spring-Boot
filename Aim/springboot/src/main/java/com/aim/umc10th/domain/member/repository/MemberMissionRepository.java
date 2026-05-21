package com.aim.umc10th.domain.member.repository;

import com.aim.umc10th.domain.member.entity.Member;
import com.aim.umc10th.domain.member.enums.MissionStatus;
import com.aim.umc10th.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    Page<MemberMission> findAllByMemberAndStatus(Member member, MissionStatus status, PageRequest pageRequest);
}
