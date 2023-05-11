package com.kadai.omise.controller;

import com.kadai.omise.domain.LoginForm;
import com.kadai.omise.domain.Member;
import com.kadai.omise.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/* ホームController */
@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;

    /* ログイン画面へ移動 */
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    /* ログイン */
    @PostMapping("/login/pro")
    public String loginPro(@ModelAttribute @Validated LoginForm loginForm,
                           BindingResult bindingResult,
                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {

            return "login";
        }

        Member loginMember = homeService.login(loginForm.getEmail(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("Login Failed", "Login Failed");

            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(String.valueOf(loginMember), loginMember.getEmail());

        return "redirect:/";
    }

    /* ログアウト */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // session clear
        }

        return "redirect:/";
    }
}
