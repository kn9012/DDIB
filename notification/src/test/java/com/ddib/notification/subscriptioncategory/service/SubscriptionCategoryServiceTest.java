//package com.ddib.notification.subscriptioncategory.service;
//
//
//import com.ddib.notification.subscriptioncategory.domain.SubscriptionCategory;
//import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
//import com.ddib.notification.subscriptioncategory.repository.SubscriptionCategoryRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static reactor.core.publisher.Mono.when;
//
//@ExtendWith(MockitoExtension.class)
//public class SubscriptionCategoryServiceTest {
//    @InjectMocks
//    SubscriptionCategoryService subscriptionCategoryService;
//
//    MockMvc mockMvc;
//
//    @Mock
//    SubscriptionCategoryRepository subscriptionCategoryRepository;
//
//    @Test
//    @DisplayName("구독 알람 신청 서비스")
//    public void applyNotification() throws Exception {
//        List<SubscriptionCategoryRequestDto> requestDtoList = new ArrayList<>();
//        requestDtoList.add(new SubscriptionCategoryRequestDto("패션"));
//        requestDtoList.add(new SubscriptionCategoryRequestDto("잡화"));
//
//        when(subscriptionCategoryRepository.save(any(SubscriptionCategory.class))).thenReturn(null);
//    }
//}
