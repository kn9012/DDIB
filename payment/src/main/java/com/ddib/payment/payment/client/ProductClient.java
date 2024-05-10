package com.ddib.payment.payment.client;

import com.ddib.payment.payment.dto.request.ProductStockDecreaseRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "productClient", url = "http://k10c102.p.ssafy.io:8082/api/product")
public interface ProductClient {

    @PutMapping("/stock/decrease")
    public void decreaseStock(@RequestBody ProductStockDecreaseRequestDto productStockDecreaseRequestDto);

}
