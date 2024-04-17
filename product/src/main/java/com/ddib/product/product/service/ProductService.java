package com.ddib.product.product.service;

import com.ddib.product.common.file.util.S3Uploader;
import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductDetail;
import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final S3Uploader s3Uploader;

    public void createProduct(List<MultipartFile> thumbnails, List<MultipartFile> details, ProductCreateRequestDto dto) {
        log.info("PRODUCT SERVICE : SAVE PRODUCT : {}", dto.getName() );
        List<String> urls = s3Uploader.storeImages("details", details);
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getTotalStock())
                .totalStock(dto.getTotalStock())
                .discount(dto.getDiscount())
                .eventDate(dto.getEventDate())
                .eventStartTime(dto.getEventStartTime())
                .eventEndTime(dto.getEventEndTime())
                .details(ProductDetail.of(urls)).build();
        productRepository.save(product);
    }
}
