package com.jiwooja.jiwoojaserver.controller.pagecontroller;

import com.jiwooja.jiwoojaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/JIUJA")
public class AllPageController {
    @Autowired
    private UserService userService;

    /* 메인 페이지 */
    @RequestMapping(value = "/main")
    public String openMain(Model model) {
        model.addAttribute("params", userService.loginTf());    // 로그인 판별
        return "/main";
    }

    /* 로그인 페이지 */
    @RequestMapping(value = "/login")
    public String openLogin(Model model) {
        model.addAttribute("params", userService.loginTf());    // 로그인 판별
        return "/login";
    }

    /* 회원가입 페이지 */
    @RequestMapping(value = "/join/membership")
    public String openJoinMembership(Model model) {
        model.addAttribute("params", userService.loginTf());    // 로그인 판별
        return "/mine/joinMembership";
    }

    /* 회원가입 성공 페이지 */
    @RequestMapping(value = "/join/membershipfin")
    public String openJoinMembershipFin(Model model) {
        model.addAttribute("params", userService.loginTf());    // 로그인 판별
        return "/mine/joinMembershipFin";
    }

}
