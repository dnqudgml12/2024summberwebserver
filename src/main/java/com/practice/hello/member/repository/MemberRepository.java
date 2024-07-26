package com.practice.hello.member.repository;

import com.practice.hello.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUid(String uid);

    Optional<Member> findByNickname(String nickname);
}
