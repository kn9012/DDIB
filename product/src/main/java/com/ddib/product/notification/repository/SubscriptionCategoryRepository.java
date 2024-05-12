package com.ddib.product.notification.repository;

import com.ddib.product.notification.domain.SubscriptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionCategoryRepository extends JpaRepository<SubscriptionCategory, Integer> {

    @Query("select sc from SubscriptionCategory sc where sc.subscriptionCategory = :subscriptionCategory")
    List<SubscriptionCategory> findBySubscriptionCategory(String subscriptionCategory);
}
