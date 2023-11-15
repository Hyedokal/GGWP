package com.ggwp.noticeservice.repository;

import com.ggwp.noticeservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findMemberById(Long id);
}
