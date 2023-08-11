package com.jiwooja.jiwoojaserver.reserve;

import com.jiwooja.jiwoojaserver.pointLog.dto.PointLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    /**
     * 포인트 결제
     * @param  pointLogDto
     * @return Long
     */
    @PostMapping(value="/pay/point")
    public boolean payPoint(@RequestBody PointLogDto pointLogDto){
        return reserveService.payPoint(pointLogDto);
    }


}
