package com.kadai.omise.service;

import com.kadai.omise.domain.Owner;
import com.kadai.omise.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/*
    オーナーService
*/
@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    /*
        登録処理
        @param Owner owner
    */
    public void save(Owner owner) throws Exception {
        ownerRepository.save(owner);
    }

    /*
        オーナー重複バリデーション処理 (email)
        @param String email
    */
    public boolean validateDuplicateEmail(String email) {

        return ownerRepository.existsByEmail(email);
    }

    /*
        ログイン処理
        @param String email
        @param String password
    */
    public Owner login(String email, String password) {
        return ownerRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
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

    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId).get();
    }

    /*
        mypage修正：情報修正
        @param Owner owner
    */
    @Transactional
    public void update(Owner owner) {
        Owner persistence = ownerRepository.findById(owner.getId()).get();

        persistence.setLastname(owner.getLastname());
        persistence.setFirstname(owner.getFirstname());
        persistence.setPassword(owner.getPassword());

        ownerRepository.save(persistence);
    }
}