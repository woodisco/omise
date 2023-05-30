package com.kadai.omise.controller;

import com.kadai.omise.domain.LoginForm;
import com.kadai.omise.domain.Member;
import com.kadai.omise.domain.Owner;
import com.kadai.omise.service.MemberService;
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
    会員Controller
*/
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private OwnerService ownerService;

    /*
        登録画面へ移動
    */
    @GetMapping("/joinMember")
    public String joinMember() {

        return "member/joinMember";
    }

    /*
        登録処理
        @param Member member
        @param Errors errors
        @param Model model
    */
    @PostMapping("/joinMember/pro")
    public String joinMemberPro(@Valid Member member, Errors errors, Model model) throws Exception {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/joinMember";
        }

        // email重複バリデーション
        boolean checkEmail = memberService.validateDuplicateEmail(member.getEmail());
        if (checkEmail == true) {
            model.addAttribute("checkEmail", "Email is already exists");

            return "member/joinMember";
        }

        memberService.save(member);

        return "member/memberLogin";
    }

    /*
        ログイン画面へ移動
    */
    @GetMapping("/memberLogin")
    public String memberLogin() {

        return "member/memberLogin";
    }

    /*
        ログイン処理
        @param LoginForm loginForm
        @param Errors errors
        @param Model model
        @param HttpSession session
    */
    @PostMapping("/memberLogin/pro")
    public String memberLoginPro(@Valid LoginForm loginForm,
                           Errors errors,
                           Model model,
                           HttpSession session) {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/memberLogin";
        }

        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        // ログイン情報が正しくない場合
        if (loginMember == null) {
            model.addAttribute("loginFail", "ログイン情報が正しくありません。");

            return "member/memberLogin";
        // ログイン情報が正しい場合
        } else {
            session.setAttribute("memberId", loginMember.getId());
        }

        return "redirect:/";
    }

    /*
        ログアウト処理
        @param HttpSession session
    */
    @PostMapping("/memberLogout")
    public String memberLogout(HttpSession session) {

        if (session.getAttribute("memberId") != null) {
            session.removeAttribute("memberId"); // session clear
        }

        return "redirect:/";
    }

    /*
        mypage画面へ移動
        @param HttpSession session
        @param Model model
    */
    @GetMapping("/memberMypage")
    public String memberMypage(HttpSession session, Model model) {

        Long memberId = (Long) session.getAttribute("memberId");

        // ログイン出来ていない場合
        if (memberId == null) {

            return "member/memberLogin";
        }

        // ログインが出来ている場合
        // 会員情報を取得
        Member loginMember = memberService.findById(memberId);
        model.addAttribute("loginMember", loginMember);

        return "member/memberMypage";
    }

    /*
        mypage修正：情報修正
        @param Member member
    */
    @PostMapping("/memberMypage/pro")
    public String memberMypagePro(Member member) {

        memberService.update(member);

        return "redirect:/";
    }
}