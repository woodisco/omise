package com.kadai.omise.controller;

import com.kadai.omise.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/joinMember")
    public String addMember() {
        return "joinMember";
    }
}
