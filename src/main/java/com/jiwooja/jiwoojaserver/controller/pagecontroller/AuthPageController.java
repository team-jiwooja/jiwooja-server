package com.jiwooja.jiwoojaserver.controller.pagecontroller;

import com.jiwooja.jiwoojaserver.dto.PointDto;
import com.jiwooja.jiwoojaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mine/JIUJA")
public class AuthPageController {

    @Autowired
    private UserService userService;

    /* 내 정보 페이지 */
    @RequestMapping(value = "/mypage")
    public String openMypage(Model model) {
        model.addAttribute("infoBasic", userService.getUserInfo());
        return "/mypage/mypage";
    }


    /* =============================================================================
     * 팝업 페이지 모음
     * ============================================================================= */

    /* 팝업 - 포인트 충전 메인 페이지 */
    @RequestMapping(value = "/popup/pointCharge/main")
    public String openPointChargeMain(Model model) {
        model.addAttribute("pointTotal", userService.getUserInfo().getPointsTotal());
        return "/point/popupPointChargeMain";
    }

    /* 팝업 - 포인트 충전 무통장입금 & 대리입금 분기 */
    @RequestMapping(value = "/popup/pointCharge/pay", method = RequestMethod.POST)
    public String openPointCharge(PointDto pointDto, Model model) {
        model.addAttribute("username", userService.getUserInfo().getUsername());
        model.addAttribute("point", pointDto.getPurchasePrice());
        if ("A".equals(pointDto.getPaySep())){
            return "/point/popupPointChargeAccount";
        } else {
            return "/point/popupPointChargeUser";
        }
    }

    /* 팝업 - 예매 취소(환불) 페이지 */
    @RequestMapping(value = "/refund")
    public String openRefund() {
        return "/mypage/refund1";
    }

    /* 팝업 - 예매 취소(환불) 페이지 */
    @RequestMapping(value = "/refund/fin")
    public String openRefundFin() {
        return "/mypage/refund2";
    }



}
