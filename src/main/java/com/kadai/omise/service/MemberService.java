package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* 会員Service */
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    /* 登録 */
    public void save(Member member) throws Exception {
        validateDuplicateMember(member);

        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(member1 -> {throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
