package com.ddib.seller.controller;

import com.ddib.seller.seller.controller.SellerController;
import com.ddib.seller.seller.domain.Seller;
import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.repository.SellerRepository;
import com.ddib.seller.seller.service.seller.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SellerControllerTest {
    @InjectMocks
    SellerController sellerController;

    MockMvc mockMvc;

    @Mock
    SellerService sellerService;

    @Mock
    SellerRepository sellerRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
    }

    @Test
    @DisplayName("판매회원 정보 조회 Controller Test")
    public void userDetails() throws Exception {
        // given
        Seller seller = Seller.builder()
                .sellerEmail("kn9012@naver.com")
                .companyName("SSAFY")
                .businessNumber("12345678")
                .ceoName("김유나")
                .ceoEmail("kn9012@naver.com")
                .ceoPhone("01012345678")
                .build();

        sellerRepository.save(seller);

        // when, then
        mockMvc.perform(get("/api/seller") // 해당 url로 GET 요청을 보냄
                        .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                .andExpect(status().isOk()) // HTTP 200 기대
                .andDo(print()); // 화면에 결과 출력
    }

    @Test
    @DisplayName("판매회원 정보 수정 Controller Test")
    public void userInfoModify() throws Exception {
        // given
        Seller seller = Seller.builder()
                .sellerEmail("kn9012@naver.com")
                .companyName("SSAFY")
                .businessNumber("12345678")
                .ceoName("김유나")
                .ceoEmail("kn9012@naver.com")
                .ceoPhone("01012345678")
                .build();

        sellerRepository.save(seller);

        // given
        SellerModifyRequestDto requestDto = SellerModifyRequestDto.builder()
                .companyName("SSAFY")
                .businessNumber("12345678")
                .ceoName("한재현")
                .ceoEmail("kn9012@naver.com")
                .ceoPhone("01012345678")
                .build();

        // when, then
        mockMvc.perform(put("/api/seller")
                        .contentType(MediaType.APPLICATION_JSON) // json 형식으로 데이터를 보냄을 명시
                        .content(objectMapper.writeValueAsString(requestDto)) // input 값 json형식의 String으로 만들기 위해 objectMapper를 사용
                        .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("판매회원 탈퇴 Controller Test")
    public void userDelete() throws Exception {
        // given
        Seller seller = Seller.builder()
                .sellerEmail("kn9012@naver.com")
                .companyName("SSAFY")
                .businessNumber("12345678")
                .ceoName("김유나")
                .ceoEmail("kn9012@naver.com")
                .ceoPhone("01012345678")
                .build();

        sellerRepository.save(seller);

        // when, then
        mockMvc.perform(delete("/api/seller")
                        .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                .andExpect(status().isOk())
                .andDo(print());

        assertNull(sellerRepository.findBySellerEmail("kn9012@google.com"), "seller가 null이 아닙니다.");
    }

    @Test
    @DisplayName("로그아웃 Controller Test")
    public void logout() throws Exception {
        mockMvc.perform(post("/api/seller/logout")
                        .with(csrf()))
                .andExpect(unauthenticated()); // 로그아웃 되었기 때문에 인증되지 않은 상태
    }
}