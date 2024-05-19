package com.ddib.payment.payment.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoProperties {

    @Value("${api.kakao.approval-url}")
    public String kakaoApprovalUrl;

    @Value("${api.kakao.cancel-url}")
    public String kakaoCancelUrl;

    @Value("${api.kakao.fail-url}")
    public String kakaoFailUrl;


    @Value("${redirect.order.complete-url}")
    public String orderCompleteUrl;

    @Value("${redirect.order.fail-url}")
    public String orderFailUrl;

    @Value("${redirect.pay.cancel-url}")
    public String payCancelUrl;

    @Value("${redirect.pay.fail-url}")
    public String payFailUrl;

}
