package com.kadai.omise.service;

import com.kadai.omise.domain.Member;
import com.kadai.omise.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/*
    会員Service
*/
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    /*
        登録処理
        @param Member member
    */
    public void save(Member member) throws Exception {
        memberRepository.save(member);
    }

    /*
        会員登録重複バリデーション処理 (email)
        @param Member member
    */
    public boolean validateDuplicateEmail(String email) {

        return memberRepository.existsByEmail(email);
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

    /*
        mypage画面の会員情報取得
        @param Long id
    */
    public Member findById(Long id) {

        return memberRepository.findById(id).get();
    }

    /*
        mypage修正：会員情報修正
        @param Member member
    */
    @Transactional
    public void update(Member member) {
        Member persistence = memberRepository.findById(member.getId()).get();

        persistence.setLastname(member.getLastname());
        persistence.setFirstname(member.getFirstname());
        persistence.setPassword(member.getPassword());

        memberRepository.save(persistence);
    }
}
