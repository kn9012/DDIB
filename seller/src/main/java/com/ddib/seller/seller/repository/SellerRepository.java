package com.ddib.seller.seller.repository;

import com.ddib.seller.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Seller findBySellerId(Integer sellerId);
    Seller findBySellerEmail(String email);
    void deleteBySellerId(Integer sellerId);
    Seller findSellerIdBySellerEmail(String email);
}
