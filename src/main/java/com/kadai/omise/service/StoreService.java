package com.kadai.omise.service;

import com.kadai.omise.domain.Store;
import com.kadai.omise.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public void save(Store store, MultipartFile file) throws IOException {
        // route
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        // create filename
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);
        store.setFilename(fileName);
        store.setFilepath("/files/" + fileName);

        storeRepository.save(store);
    }
}
