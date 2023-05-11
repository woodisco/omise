package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* ホームService */
@Service
public class HomeService {
    @Autowired
    private MemberRepository memberRepository;

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
