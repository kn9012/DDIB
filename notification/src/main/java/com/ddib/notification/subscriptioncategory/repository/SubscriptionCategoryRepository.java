package com.ddib.notification.subscriptioncategory.repository;

import com.ddib.notification.subscriptioncategory.domain.SubscriptionCategory;
import com.ddib.notification.subscriptioncategory.dto.response.SubscriptionCategoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionCategoryRepository extends JpaRepository<SubscriptionCategory, Integer> {
    void deleteSubscriptionCategoriesByUserUserId(Integer userId);
    SubscriptionCategory findSubscriptionCategoryByUserUserId(Integer userId);
}
