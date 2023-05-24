package com.kadai.omise.controller;

import com.kadai.omise.domain.Member;
import com.kadai.omise.service.MemberService;
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

        return "login";
    }

    /*
        mypage画面へ移動
        @param HttpSession session
        @param Model model
    */
    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {

        // ログイン出来ていない場合
        if (session.getAttribute("id") ==  null) {

            return "login";
        }

        Long loginId = (Long) session.getAttribute("id");

        // 会員情報を取得
        Member loginMember =  memberService.findById(loginId);
        model.addAttribute("loginMember", loginMember);

        return "member/mypage";
    }

    /*
        mypage修正：会員情報修正
        @param Member member
    */
    @PostMapping("/mypage/pro")
    public String myPagePro(Member member) {

        memberService.update(member);

        return "redirect:/";
    }
}
