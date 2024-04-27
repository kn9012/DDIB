package com.ddib.seller.seller.handler;

import com.ddib.seller.seller.dto.response.CustomOAuth2User;
import com.ddib.seller.seller.service.oauth.RedisService;
import com.ddib.seller.seller.setting.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @Value("${releaseHostName}")
    private String releaseHostName;

    @Value("${access.token.expiration.time}")
    private Long accessExpireMs;

    @Value("${refresh.token.expiration.time}")
    private Long refreshExpireMs;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String email = customUserDetails.getEmail();

        log.info("success핸들러 " + email);

        // 권한을 찾아서 권한 설정해줌
        // authentication.getAuthorities()를 호출하여
        // 현재 사용자에게 부여된 권한을 나타내는 GrantedAuthority 객체의 컬렉션을 얻습니다.
        // 이 컬렉션에는 사용자가 가진 모든 권한이 포함되어 있습니다.
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();

        String access = jwtUtil.createJwt("access", email, accessExpireMs);
        String refresh = jwtUtil.createJwt("refresh", email, refreshExpireMs);

        log.info("accesstoken : " + access);
        log.info("refreshtoken : " + refresh);

        // redis 에 담아서 refresh token 관리
        redisService.setValues(email, refresh, refreshExpireMs);

//        response.addCookie(createCookie("refresh", refresh));
//        response.addCookie(createCookie("Authorization", access));

        response.addHeader("Authorization", "Bearer " + access);
        response.addHeader("Authorization", "Bearer " + access);
        log.info("response " + response.getHeader("Authorization"));

//        response.sendRedirect("http://" + releaseHostName);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }
}
