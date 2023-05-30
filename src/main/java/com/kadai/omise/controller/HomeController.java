package com.kadai.omise.controller;

import com.kadai.omise.domain.Store;
import com.kadai.omise.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

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
}
