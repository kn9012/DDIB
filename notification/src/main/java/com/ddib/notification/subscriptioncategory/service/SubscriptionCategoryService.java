package com.ddib.notification.subscriptioncategory.service;

import com.ddib.notification.subscriptioncategory.domain.SubscriptionCategory;
import com.ddib.notification.subscriptioncategory.dto.request.SubscriptionCategoryRequestDto;
import com.ddib.notification.subscriptioncategory.dto.response.SubscriptionCategoryResponseDto;
import com.ddib.notification.subscriptioncategory.repository.SubscriptionCategoryRepository;
import com.ddib.notification.user.domain.User;
import com.ddib.notification.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionCategoryService {
    private final SubscriptionCategoryRepository subscriptionCategoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSubscriptionCategory(SubscriptionCategoryRequestDto requestDto, Integer userId) {
        User user = userRepository.findByUserId(userId);

        if (user.isSubscribed()) {
            subscriptionCategoryRepository.findSubscriptionCategoryByUserUserId(userId).updateSubscriptionCategory(requestDto);
        }

        else {
            SubscriptionCategory subscriptionCategory = SubscriptionCategory.builder()
                    .user(user)
                    .subscribeFashion(requestDto.isSubscribeFashion())
                    .subscribeBeauty(requestDto.isSubscribeBeauty())
                    .subscribeFood(requestDto.isSubscribeFood())
                    .subscribeAppliance(requestDto.isSubscribeAppliance())
                    .subscribeSports(requestDto.isSubscribeSports())
                    .subscribeLiving(requestDto.isSubscribeLiving())
                    .subscribePet(requestDto.isSubscribePet())
                    .subscribeTravel(requestDto.isSubscribeTravel())
                    .build();

            subscriptionCategoryRepository.save(subscriptionCategory);
        }
    }

    @Transactional
    public void deleteSubscriptionCategory(Integer userId) {
        subscriptionCategoryRepository.deleteSubscriptionCategoriesByUserUserId(userId);
    }

    public SubscriptionCategoryResponseDto findSubscriptionList(Integer userId) {
        SubscriptionCategory subscriptionCategory = subscriptionCategoryRepository.findSubscriptionCategoryByUserUserId(userId);
        return SubscriptionCategoryResponseDto.builder()
                .subscribeAppliance(subscriptionCategory.isSubscribeAppliance())
                .subscribeBeauty(subscriptionCategory.isSubscribeBeauty())
                .subscribeFashion(subscriptionCategory.isSubscribeFashion())
                .subscribeFood(subscriptionCategory.isSubscribeFood())
                .subscribeLiving(subscriptionCategory.isSubscribeLiving())
                .subscribePet(subscriptionCategory.isSubscribePet())
                .subscribeSports(subscriptionCategory.isSubscribeSports())
                .subscribeTravel(subscriptionCategory.isSubscribeTravel())
                .build();
    }
}
