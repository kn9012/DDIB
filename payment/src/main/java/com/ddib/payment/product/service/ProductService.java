package com.ddib.payment.product.service;

import com.ddib.payment.product.domain.Product;
import com.ddib.payment.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public int checkStock(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get().getStock();
    }

    @Transactional
    public void updateStock(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        int currentStock = product.get().getStock();

        if(currentStock >= 1) {
            product.get().updateStock(currentStock);
        }
    }

}
