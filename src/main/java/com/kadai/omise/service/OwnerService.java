package com.kadai.omise.service;

import com.kadai.omise.domain.Owner;
import com.kadai.omise.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        @param Member member
    */
    public boolean validateDuplicateEmail(String email) {

        return ownerRepository.existsByEmail(email);
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
