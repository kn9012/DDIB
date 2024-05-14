package com.ddib.seller.seller.service.oauth;

import com.ddib.seller.seller.dto.response.UserResponseDto;
import com.ddib.seller.seller.repository.SellerRepository;
import com.ddib.seller.seller.setting.jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReissueServiceImpl implements ReissueService {
    @Value("${access.token.expiration.time}")
    private Long accessExpireMs;

    @Value("${refresh.token.expiration.time}")
    private Long refreshExpireMs;

    private final JWTUtil jwtUtil;
    private final RedisService redisService;
    private final SellerRepository sellerRepository;

    public ReissueServiceImpl(JWTUtil jwtUtil, RedisService redisService, SellerRepository sellerRepository) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ResponseEntity<?> reissueRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        // ref 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            // response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        // 만료 체크
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            // response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            // response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // DB에 저장되어 있는지 확인
        Boolean isExist = redisService.checkExistsValue(refresh);
        if (!isExist) {

            // response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);
        Integer sellerId = sellerRepository.findSellerIdBySellerEmail(email).getSellerId();

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", email, accessExpireMs);
        String newRefresh = jwtUtil.createJwt("refresh", email, refreshExpireMs);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        redisService.deleteValues(refresh);
        redisService.setValues(email, newRefresh, refreshExpireMs);

        //response
        response.setHeader("Authorization", "Bearer " + newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
        response.addCookie(createCookie("num", String.valueOf(sellerId)));

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(false);

        return cookie;
    }
}
