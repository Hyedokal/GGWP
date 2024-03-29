package com.ggwp.memberservice.repository;

import com.ggwp.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByUuid (String uuid);

    @Query("SELECT m FROM Member m WHERE m.lolNickname = :lolNickName AND m.tag = :tag")
    Member findByLolNickNameAndTag(String lolNickName, String tag);



    boolean existsByEmail(String email); // 이메일 중복검사


    @Query("SELECT COUNT(m) FROM Member m WHERE m.lolNickname = :lolNickname AND m.tag = :tag")
    Long countByLolNicknameAndTag(@Param("lolNickname") String lolNickname, @Param("tag") String tag);


    Member findByLolNicknameAndTag(String lolNickname, String tag);

    Optional<Member> findOptionalMemberByLolNicknameAndTag(String lolNickname, String tag);


}

