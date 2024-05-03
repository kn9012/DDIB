package com.ddib.payment.product.service;

import com.ddib.payment.product.domain.Product;
import com.ddib.payment.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;

    public int checkStock(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get().getStock();
    }

    public int checkStockWithLock(int productId) {
        // 특정 이름으로 Lock 정의
        final String lockName = productId + ":lock";
        final RLock lock = redissonClient.getLock(lockName);
        final String worker = Thread.currentThread().getName();

        try {
            // 락 획득 시도 (20초 동안 시도하고 락을 획득할 경우 3초 후에 해제)
            boolean available = lock.tryLock(20, 3, TimeUnit.SECONDS);
            if(!available) {
                log.info("===== Lock 획득 실패 =====");
                return;
            }

            int stock = checkStock(productId);
            return stock;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public void updateStock(int productId, int quantity) {
        Optional<Product> product = productRepository.findById(productId);
        int currentStock = product.get().getStock();

        if(currentStock >= 1) {
            product.get().updateStock(currentStock, quantity);
        }
    }

}
