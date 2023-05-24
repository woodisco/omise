package com.kadai.omise.controller;

import com.kadai.omise.domain.Store;
import com.kadai.omise.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
        @param Error errors
        @param MultipartFile file
        @param Model model
    */
    @PostMapping("/joinStore/pro")
    public String joinStorePro(@Valid Store store, Errors errors, MultipartFile file, Model model) throws Exception {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = storeService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "store/joinStore";
        }

        // name重複バリデーション
        boolean checkName = storeService.validateDuplicateName(store.getName());
        System.out.println("======================="+checkName);
        if (checkName == true) {
            model.addAttribute("checkName", "Name is already exists");

            return "store/joinStore";
        }

        storeService.save(store, file);

        return "redirect:/";
    }

    /*
        修正画面へ移動
    */
    @GetMapping("/updateStore")
    public String updateStore() {

        return "store/updateStore";
    }
}
