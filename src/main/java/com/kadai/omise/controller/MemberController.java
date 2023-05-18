package com.kadai.omise.controller;

import com.kadai.omise.domain.Member;
import com.kadai.omise.service.MemberService;
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

        return "redirect:/";
    }

    /*
        mypageへ移動
    */
    @GetMapping("/myPage")
    public String myPage() {

        return "member/myPage";
    }
}
