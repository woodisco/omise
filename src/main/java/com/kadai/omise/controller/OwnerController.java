package com.kadai.omise.controller;

import com.kadai.omise.domain.Owner;
import com.kadai.omise.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/*
    オーナーController
*/
@Controller
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    /*
        登録画面へ移動
    */
    @GetMapping("/joinOwner")
    public String joinOwner() {

        return "owner/joinOwner";
    }

    /*
        登録処理
        @param Owner owner
        @param Errors errors
        @param Model model
    */
    @PostMapping("/joinOwner/pro")
    public String joinOwnerPro(@Valid Owner owner, Errors errors, Model model) throws Exception {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = ownerService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "owner/joinOwner";
        }

        // email重複バリデーション
        boolean checkEmail = ownerService.validateDuplicateEmail(owner.getEmail());
        if (checkEmail == true) {
            model.addAttribute("checkEmail", "Email is already exists");

            return "owner/joinOwner";
        }

        ownerService.save(owner);

        return "login";
    }
}
