package com.ddib.notification.notification.controller;

import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
import com.ddib.notification.subscriptioncategory.service.SubscriptionCategoryService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {
    @InjectMocks
    NotificationController notificationController;

    MockMvc mockMvc;

    @Mock
    SubscriptionCategoryService subscriptionCategoryService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    @DisplayName("구독 알림 신청 Controller Test")
    public void applyNotification() throws Exception {
        // given
        List<SubscriptionCategoryRequestDto> requestDtoList = new ArrayList<>();
        requestDtoList.add(new SubscriptionCategoryRequestDto("패션"));
        requestDtoList.add(new SubscriptionCategoryRequestDto("잡화"));

        // when, then
        mockMvc.perform(
                put("/api/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoList)))
                        .andExpect(status().isOk());

//        verify(subscriptionCategoryService).createSubscriptionCategory(refEq(requestDtoList));
    }
}