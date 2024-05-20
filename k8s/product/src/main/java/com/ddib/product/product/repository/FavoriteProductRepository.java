package com.ddib.product.product.repository;

import com.ddib.product.product.domain.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {

    Optional<FavoriteProduct> findByFavoriteProductId(int id);
}
