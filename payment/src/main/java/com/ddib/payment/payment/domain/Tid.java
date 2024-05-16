package com.ddib.payment.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "orderId", timeToLive = 3600) // 초 단위 (1시간)
public class Tid {

    @Id
    private String orderId;

    private String tid;

}
