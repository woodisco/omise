package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.domain.Store;
import com.kadai.omise.repository.MemberRepository;
import com.kadai.omise.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* ホームService */
@Service
public class HomeService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    /* ログイン */
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    /* お店Listを取得 */
    public List<Store> findAllOfStore() {
        return storeRepository.findAll();
    }

    /* 会員数取得 */
    public int findAllOfMember() {
        return memberRepository.findAll().size();
    }

    public int findAllOfAddress() {
        return storeRepository.findAllOfAddress();
    }
}