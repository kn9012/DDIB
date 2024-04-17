package com.ddib.product.product.controller;

import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestPart(value = "thumbnailImage") List<MultipartFile> thumbnails,
                                           @RequestPart(value = "productDetails") List<MultipartFile> details,
                                           @RequestBody ProductCreateRequestDto dto) {
        log.info("PRODUCT CREATE REQUEST");
        productService.createProduct(thumbnails, details, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
