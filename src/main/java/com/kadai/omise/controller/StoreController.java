package com.kadai.omise.controller;

import com.kadai.omise.domain.Store;
import com.kadai.omise.service.StoreService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/*
    お店Controller
*/
@Controller
public class StoreController {
    @Autowired
    private StoreService storeService;

    /*
        登録画面へ移動
    */
    @GetMapping("/joinStore")
    public String joinStore() {

        return "store/joinStore";
    }

    /*
        登録処理
        @param Store store
        @param Error errors
        @param MultipartFile file
        @param Model model
    */
    @PostMapping("/joinStore/pro")
    public String joinStorePro(@Valid Store store, Errors errors, MultipartFile file, Model model, HttpSession session) throws Exception {

        // エラーがある場合
        if (errors.hasErrors()) {
            // 未入力バリデーション処理
            Map<String, String> validatorResult = storeService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "store/joinStore";
        }

        // name重複バリデーション
        boolean checkName = storeService.validateDuplicateName(store.getName());
        if (checkName == true) {
            model.addAttribute("checkName", "Name is already exists");

            return "store/joinStore";
        }

        Long ownerId = (Long) session.getAttribute("ownerId");

        storeService.save(store, file, ownerId);

        return "redirect:/";
    }

    /*
        修正お店Listへ移動
        @param Model model
        @param HttpSession session
        @param Pageable pageable
    */
    @GetMapping("/updateStoreList")
    public String updateStoreList(Model model,
                                  @PageableDefault(page = 0, size = 9, sort = "store_id", direction = Sort.Direction.DESC)
                                  Pageable pageable, HttpSession session) {

        Long ownerId = (Long) session.getAttribute("ownerId");

        // ページング機能処理
        Page<Store> updateStoreList = storeService.updateStoreList(ownerId, pageable);

        int nowPage = updateStoreList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, updateStoreList.getTotalPages());

        model.addAttribute("updateStoreList", updateStoreList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "store/updateStoreList";
    }

    /*
        お店修正画面へ移動
        @param Long storeId
        @param Model model
    */
    @GetMapping("/updateStore/{id}")
    public String updateStore(@PathVariable("id") Long id, Model model) {

        Store storeInfo = storeService.findById(id);
        model.addAttribute("storeInfo", storeInfo);

        return "store/updateStore";
    }

    /*
        お店修正
        @param Store store
        @param MultipartFile file
    */
    @PostMapping("/updateStore/pro")
    public String updateStorePro(Store store, MultipartFile file) throws IOException {

        storeService.updateStore(store, file);

        return "redirect:/";
    }
}