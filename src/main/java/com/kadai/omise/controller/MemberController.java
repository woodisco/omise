package com.kadai.omise.controller;

import com.kadai.omise.domain.Member;
import com.kadai.omise.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/* 会員Controller */
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    /* 登録画面へ移動 */
    @GetMapping("/joinMember")
    public String joinMember() {

        return "member/joinMember";
    }

    /* 登録 */
    @PostMapping("/joinMember/pro")
    public String joinMemberPro(Member member) throws Exception {
        memberService.save(member);

        return "index";
    }
}
