package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {

        return memberRepository.save(member);
    }
}
