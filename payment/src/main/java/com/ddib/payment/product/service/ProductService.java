package com.ddib.payment.product.service;

import com.ddib.payment.product.domain.Product;
import com.ddib.payment.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public int checkStock(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get().getStock();
    }

    @Transactional
    public void updateStock(int productId, int quantity) {
        log.info("===== " + Thread.currentThread().getName() + " minus " + quantity + " stock start =====");

        Optional<Product> product = productRepository.findById(productId);
        int currentStock = product.get().getStock();

        if(currentStock - quantity >= 0) {
            product.get().updateStock(currentStock, quantity);
            productRepository.flush();
        }
    }

    @Transactional
    public void updateStockByRefund(int productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);
        product.get().updateStockByRefund(quantity);
        productRepository.flush();
    }

}
