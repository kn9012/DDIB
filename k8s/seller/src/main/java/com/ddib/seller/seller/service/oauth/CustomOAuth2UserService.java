package com.ddib.seller.seller.service.oauth;

import com.ddib.seller.seller.domain.Seller;
import com.ddib.seller.seller.dto.response.CustomOAuth2User;
import com.ddib.seller.seller.dto.response.KakaoResponseDto;
import com.ddib.seller.seller.dto.response.OAuth2ResponseDto;
import com.ddib.seller.seller.dto.response.UserResponseDto;
import com.ddib.seller.seller.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser 호출!!!!!!!");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2ResponseDto oAuth2Response;

        if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponseDto(oAuth2User.getAttributes());
        } else { return null; }

        String email = oAuth2Response.getEmail();
        Seller existData = sellerRepository.findBySellerEmail(email);

        if (existData == null) {
            Seller seller = Seller.builder()
                    .ceoName(oAuth2Response.getNickName())
                    .sellerEmail(oAuth2Response.getEmail())
                    .build();

            sellerRepository.save(seller);

            UserResponseDto responseDto = UserResponseDto.builder()
                    .name(seller.getCeoName())
                    .email(seller.getSellerEmail())
                    .build();

            return new CustomOAuth2User(responseDto);
        } else {
            UserResponseDto responseDto = UserResponseDto.builder()
                    .name(existData.getCeoName())
                    .email(existData.getSellerEmail())
                    .build();
            return new CustomOAuth2User(responseDto);
        }
    }
}
