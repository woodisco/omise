package com.kadai.omise.controller;

import com.kadai.omise.domain.Store;
import com.kadai.omise.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/*
    お店Controller
*/
@Controller
public class StoreController {
    @Autowired
    private StoreService storeService;

    /*
        登録画面へ移動
    */
    @GetMapping("/joinStore")
    public String joinStore() {

        return "store/joinStore";
    }

    /*
        登録処理
        @param Store store
        @param MultipartFile file
    */
    @PostMapping("/joinStore/pro")
    public String joinStorePro(Store store, MultipartFile file) throws Exception {
        storeService.save(store, file);

        return "redirect:/";
    }
}
