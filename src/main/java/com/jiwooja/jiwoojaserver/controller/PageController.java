package com.jiwooja.jiwoojaserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/JIUJA")
public class PageController {
    @RequestMapping(value = "/login")
    public String openLogin() {
        return "/login";
    }
}
