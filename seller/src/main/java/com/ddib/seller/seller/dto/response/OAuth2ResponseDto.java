package com.ddib.seller.seller.dto.response;

import jakarta.validation.constraints.NotNull;

public interface OAuth2ResponseDto {
    //이메일
    @NotNull
    String getEmail();
    //사용자 실명 (설정한 이름)
    String getNickName();
}
