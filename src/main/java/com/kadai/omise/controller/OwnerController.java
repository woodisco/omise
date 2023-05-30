package com.kadai.omise.controller;

import com.kadai.omise.domain.LoginForm;
import com.kadai.omise.domain.Member;
import com.kadai.omise.domain.Owner;
import com.kadai.omise.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

        return "owner/ownerLogin";
    }

    /*
        ログイン画面へ移動
    */
    @GetMapping("/ownerLogin")
    public String ownerLogin() {

        return "owner/ownerLogin";
    }

    /*
        ログイン処理
        @param LoginForm loginForm
        @param Errors errors
        @param Model model
        @param HttpServletRequest request
    */
    @PostMapping("/ownerLogin/pro")
    public String ownerLoginPro(@Valid LoginForm loginForm,
                                 Errors errors,
                                 Model model,
                                 HttpSession session) {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = ownerService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "owner/ownerLogin";
        }

        Owner loginOwner = ownerService.login(loginForm.getEmail(), loginForm.getPassword());

        // ログイン情報が正しくない場合
        if (loginOwner == null) {
            model.addAttribute("loginFail", "ログイン情報が正しくありません。");

            return "owner/ownerLogin";
        // ログイン情報が正しい場合
        } else {
            session.setAttribute("ownerId", loginOwner.getId());
        }

        return "redirect:/";
    }

    /*
        ログアウト処理
        @param HttpSession session
    */
    @PostMapping("/ownerLogout")
    public String ownerLogout(HttpSession session) {

        if (session.getAttribute("ownerId") != null) {
            session.removeAttribute("ownerId"); // session clear
        }

        return "redirect:/";
    }

    /*
        mypage画面へ移動
        @param HttpSession session
        @param Model model
    */
    @GetMapping("/ownerMypage")
    public String memberMypage(HttpSession session, Model model) {

        Long ownerId = (Long) session.getAttribute("ownerId");

        // ログイン出来ていない場合
        if (ownerId == null) {

            return "owner/ownerLogin";
        }

        // ログインが出来ている場合
        // オーナー情報を取得
        Owner loginOwner =  ownerService.findById(ownerId);
        model.addAttribute("loginOwner", loginOwner);

        return "owner/ownerMypage";
    }

    /*
        mypage修正：情報修正
        @param Owner owner
    */
    @PostMapping("/ownerMypage/pro")
    public String ownerMypagePro(Owner owner) {

        System.out.println("======================="+owner);
        ownerService.update(owner);

        return "redirect:/";
    }

    /*
        お店リスト画面へ移動
        @param HttpSession session
        @param Model model
    */
    @GetMapping("/listStore")
    public String listStore(HttpSession session, Model model) {

        // ログイン出来ていない場合
        if (session.getAttribute("ownerId") ==  null) {

            return "owner/ownerLogin";
        }

        Long ownerId = (Long) session.getAttribute("ownerId");
        // 会員情報を取得
        //List<Owner> loginOwner =  ownerService.searchStoreOfOwner(ownerId);
        //model.addAttribute("loginOwner", loginOwner);

        return "owner/listStore";
    }
}