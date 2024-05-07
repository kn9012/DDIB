package com.ddib.seller.seller.repository;

import com.ddib.seller.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Seller findBySellerId(Integer userId);
    void deleteBySellerId(Integer userId);
    Integer findSellerIdBySellerEmail(String email);
}
