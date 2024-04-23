package com.ddib.notification.subscriptioncategory.repository;

import com.ddib.notification.subscriptioncategory.domain.SubscriptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionCategoryRepository extends JpaRepository<SubscriptionCategory, Integer> {
}
