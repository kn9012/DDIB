package com.ddib.payment.order.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class OrderIdGenerator {

    private static String orderId;

    /**
     * 문자(1)+날짜(6)+랜덤숫자(6) 로 이루어진 주문번호 생성
     */
    public static String generateOrderId() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dateString = sdf.format(date);

        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder();
        for(int i=0; i<6; i++) {
            randomString.append(random.nextInt(10));
        }

        orderId = "D" + dateString + randomString;
        return orderId;
    }

}
