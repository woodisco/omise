package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.domain.Store;
import com.kadai.omise.repository.MemberRepository;
import com.kadai.omise.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
    ホームService
*/
@Service
public class HomeService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    /*
        ログイン処理
        @param String email
        @param String password
    */
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    /*
        お店Listを取得
    */
    public List<Store> findAllOfStore() {

        return storeRepository.findAll();
    }

    /*
        会員数取得
    */
    public int findAllOfMember() {

        return memberRepository.findAll().size();
    }

    /*
        お店の都市数取得
    */
    public int findAllOfAddress() {

        return storeRepository.findAllOfAddress();
    }

    /*
        お店Listを取得
        @param Pageable pageable
    */
    public Page<Store> list(Pageable pageable) {

        return storeRepository.findAll(pageable);
    }

    /*
        お店Listを取得 (検索機能処理)
        @param String searchName
        @param String searchRoute
        @param Pageable pageable
    */
    public Page<Store> searchList(String searchName, String searchRoute, Pageable pageable) {

        return storeRepository.findByNameOrRoute(searchName, searchRoute, pageable);
    }

    /*
        カテゴリ取得
    */
    public List<String> findAllOfCategory() {

        return storeRepository.findAllOfCategory();
    }

    /*
        路線取得
    */
    public List<String> findAllOfRoute() {
        return storeRepository.findAllOfRoute();
    }
}