package com.ddib.product.product.controller;

import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.dto.request.ProductLikeRequestDto;
import com.ddib.product.product.dto.request.ProductStockDecreaseRequestDto;
import com.ddib.product.product.dto.request.ProductStockUpdateRequestDto;
import com.ddib.product.product.dto.response.ProductMainResponseDto;
import com.ddib.product.product.dto.response.ProductResponseDto;
import com.ddib.product.product.dto.response.ProductViewResponseDto;
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
                                           @RequestPart(value = "dto") ProductCreateRequestDto dto) {
        log.info("PRODUCT CREATE REQUEST");
        productService.createProduct(thumbnails, details, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity<ProductMainResponseDto> getMainPageData() {
        ProductMainResponseDto dto = productService.getMainPageData();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> findProductsByConditions(@RequestParam(required = false) String keyword,
                                                                             @RequestParam(required = false) String category) {
        List<ProductResponseDto> dtos = productService.findProductsByConditions(keyword, category);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // 재고량 수정
    @PutMapping("/stock/decrease")
    public ResponseEntity<?> decreaseStock(@RequestBody ProductStockDecreaseRequestDto dto) {
        productService.decreaseStock(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/stock/update")
    public ResponseEntity<?> updateStock(@RequestBody ProductStockUpdateRequestDto dto) {
        productService.updateStock(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeProduct(@RequestBody ProductLikeRequestDto dto) {
        productService.likeProduct(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 사람별 좋아요한 상품조회
    @GetMapping("/like/user/{userId}")
    public ResponseEntity<List<ProductResponseDto>> findFavoriteProductsByUserId(@PathVariable("userId") int userId) {
        List<ProductResponseDto> dtos = productService.findFavoriteProductByUserId(userId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // 해당판매자의 판매 리스트 조회
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductResponseDto>> findProductsBySellerId(@PathVariable("sellerId") int sellerId) {
        List<ProductResponseDto> dtos = productService.findProductsBySellerId(sellerId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> findProductsInWeekend() {
        List<ProductResponseDto> dtos = productService.findProductsInWeekend();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{productId}/{userId}")
    public ResponseEntity<ProductViewResponseDto> findProductByProductId(@PathVariable("productId") int productId, @PathVariable("userId") int userId) {
        ProductViewResponseDto dto = productService.findProductByProductId(productId, userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
