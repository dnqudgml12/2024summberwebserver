package com.practice.hello.member.service;


import com.practice.hello.member.entity.Member;
import com.practice.hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }


    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
