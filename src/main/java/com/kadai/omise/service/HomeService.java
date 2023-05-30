package com.kadai.omise.service;

import com.kadai.omise.domain.Store;
import com.kadai.omise.repository.MemberRepository;
import com.kadai.omise.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Page<Store> searchList(String searchName, String searchRoute, String searchCategory, Pageable pageable) {

        return storeRepository.findByNameOrRouteOrCategory(searchName, searchRoute, searchCategory, pageable);
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

    /*
        バリデーション処理
        @param Errors errors
    */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            // messageはEntityで作成したこと
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
}