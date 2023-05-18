package com.kadai.omise.controller;

import com.kadai.omise.domain.LoginForm;
import com.kadai.omise.domain.Member;
import com.kadai.omise.domain.Store;
import com.kadai.omise.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    ホームController
*/
@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;

    /*
        ホーム画面
        @param Model model
        @param Pageable pageable
        @param String searchName
    */
    @GetMapping("/")
    public String home(Model model,
                       @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                       Pageable pageable, String searchName, String searchRoute, String searchCategory) {

        List<String> totalOfCategory = new ArrayList<String>();
        List<String> totalOfRoute = new ArrayList<String>();

        // お店数取得
        int totalOfStore = homeService.findAllOfStore().size();
        // 会員数取得
        int totalOfMembers = homeService.findAllOfMember();
        // お店の都市数取得
        int totalOfAddress = homeService.findAllOfAddress();
        // カテゴリ取得
        totalOfCategory = homeService.findAllOfCategory();
        // 路線取得
        totalOfRoute = homeService.findAllOfRoute();

        model.addAttribute("totalOfMembers", totalOfMembers);
        model.addAttribute("totalOfStore", totalOfStore);
        model.addAttribute("totalOfAddress", totalOfAddress);
        model.addAttribute("totalOfCategory", totalOfCategory);
        model.addAttribute("totalOfRoute", totalOfRoute);

        // ページング機能処理
        Page<Store> list = null;
        if (searchName == null) {
            list = homeService.list(pageable);
        } else {
            list = homeService.searchList(searchName, searchRoute, searchCategory, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

    /*
        ログイン画面へ移動
    */
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    /*
        ログイン処理
        @param LoginForm loginForm
        @param Errors errors
        @param Model model
        @param HttpServletRequest request
    */
    @PostMapping("/login/pro")
    public String loginPro(@Valid LoginForm loginForm,
                           Errors errors,
                           Model model,
                           HttpSession session) {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = homeService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "login";
        }

        Member loginMember = homeService.login(loginForm.getEmail(), loginForm.getPassword());

        // ログイン情報が正しくない場合
        if (loginMember == null) {
            model.addAttribute("loginFail", "ログイン情報が正しくありません。");

            return "login";
        // ログイン情報が正しい場合
        } else {
            session.setAttribute("id", loginMember.getEmail());
        }

        return "redirect:/";
    }

    /*
        ログアウト処理
        @param HttpServletRequest request
    */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // session clear
        }

        return "redirect:/";
    }
}
