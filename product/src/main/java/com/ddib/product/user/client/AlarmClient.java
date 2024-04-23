package com.ddib.product.user.client;

import com.ddib.product.product.dto.request.ProductLikeRequestDto;

//@FeignClient(name = "alarm", url="http://j10c102.p.ssafy.io:8081")
public interface AlarmClient {

//    @PostMapping("/알람요청URL")
    public void requestAlarm(ProductLikeRequestDto dto);

}
