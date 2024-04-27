package com.ddib.seller.seller.dto.response;

import java.util.Map;

public class KakaoResponseDto implements OAuth2ResponseDto {

    private final Map<String, Object> attribute;
    private final Map<String, Object> kakaoAccount;

    public KakaoResponseDto(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getNickName() {
        return "이름 입력";
    }
}
