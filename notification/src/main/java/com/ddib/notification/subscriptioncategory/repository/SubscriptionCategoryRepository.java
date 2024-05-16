package com.ddib.notification.subscriptioncategory.repository;

import com.ddib.notification.subscriptioncategory.domain.SubscriptionCategory;
import com.ddib.notification.subscriptioncategory.dto.response.SubscriptionCategoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionCategoryRepository extends JpaRepository<SubscriptionCategory, Integer> {
    void deleteSubscriptionCategoriesByUserUserId(Integer userId);
    SubscriptionCategoryResponseDto findSubscriptionCategoriesByUserUserId(Integer userId);
}
