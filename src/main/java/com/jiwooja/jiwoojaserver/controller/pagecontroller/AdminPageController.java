package com.jiwooja.jiwoojaserver.controller.pagecontroller;

import com.jiwooja.jiwoojaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/JIUJA")
public class AdminPageController {

    @Autowired
    private UserService userService;

    /* 내 정보 페이지 */
    @RequestMapping(value = "/management")
    public String openMypage(Model model) {
        model.addAttribute("infoBasic", userService.getUserInfo());
        return "/admin/management";
    }
}
