package com.kadai.omise.service;

import com.kadai.omise.domain.Owner;
import com.kadai.omise.domain.Store;
import com.kadai.omise.repository.OwnerRepository;
import com.kadai.omise.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
    お店Service
*/
@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    /*
        お店登録処理
        @param Store store
        @param MultipartFile file
    */
    public void save(Store store, MultipartFile file, Long ownerId) throws IOException {
        // ファイル経路設定
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        // ランダムファイル名を作成
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        // オーナーの情報を取得
        Owner owner = ownerRepository.findById(ownerId).get();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);
        store.setFilename(fileName);
        store.setFilepath("/files/" + fileName);
        store.setOwner(owner);
        owner.putStore(store);

        storeRepository.save(store);
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
        お店登録重複バリデーション処理 (name)
        @param String name
    */
    public boolean validateDuplicateName(String name) {

        return storeRepository.existsByName(name);
    }

    /*
        修正お店Listへ移動
        @param Long ownerId
        @param Pageable pageable
    */
    public Page<Store> updateStoreList(Long ownerId, Pageable pageable) {

        return storeRepository.updateStoreList(ownerId, pageable);
    }

    /*
        お店修正画面へ移動
        @param Long storeId
    */
    public Store findById(Long storeId) {

        return storeRepository.findById(storeId).get();
    }

    /*
        お店修正
        @param Store store
        @param MultipartFile file
    */
    public void updateStore(Store store, MultipartFile file) throws IOException {
        Store persistence = storeRepository.findById(store.getId()).get();

        persistence.setName(store.getName());
        persistence.setCategory(store.getCategory());
        persistence.setZipcode(store.getZipcode());
        persistence.setAddress1(store.getAddress1());
        persistence.setAddress2(store.getAddress2());
        persistence.setRoute(store.getRoute());
        persistence.setContent(store.getCategory());

        // ファイル経路設定
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        File saveFile = new File(projectPath, store.getFilename());
        file.transferTo(saveFile);
        persistence.setFilename(store.getFilename());
        persistence.setFilepath(store.getFilepath());

        storeRepository.save(persistence);
    }
}