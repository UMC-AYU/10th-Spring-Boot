package com.aim.umc10th.domain.member.repository;

import com.aim.umc10th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //스프링 데이터 JPA가 findBy + ~~을 분석해서 'SELECT * FROM member WHERE ~~ = ?' 쿼리를 자동으로 만들어준다.
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);

    void deleteByName(String name);
}
