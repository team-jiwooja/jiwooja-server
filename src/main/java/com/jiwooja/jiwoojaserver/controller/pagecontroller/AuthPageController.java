package com.jiwooja.jiwoojaserver.controller.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mine/JIUJA")
public class AuthPageController {
    /* 팝업 - 포인트 충전 메인 페이지 */
    @RequestMapping(value = "/popup/pointCharge/main")
    public String openPointChargeMain() {
        return "/point/popupPointChargeMain";
    }

    /* 팝업 - 포인트 충전 무통장입금 */
    @RequestMapping(value = "/popup/pointCharge/account")
    public String openPointChargeAccount() {
        return "/point/popupPointChargeAccount";
    }

    /* 팝업 - 포인트 충전 대리입금 */
    @RequestMapping(value = "/popup/pointCharge/user")
    public String openPointChargeUser() {
        return "/point/popupPointChargeUser";
    }

}
