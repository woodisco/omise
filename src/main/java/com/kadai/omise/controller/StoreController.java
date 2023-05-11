package com.kadai.omise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/* お店Controller */
@Controller
public class StoreController {

    @GetMapping("store/joinStore")
    public String joinStore() {

        return "store/joinStore";
    }
}
