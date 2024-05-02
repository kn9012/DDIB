package com.ddib.product.product.service;

import com.ddib.product.common.file.util.S3Uploader;
import com.ddib.product.product.domain.FavoriteProduct;
import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductDetail;
import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.dto.request.ProductLikeRequestDto;
import com.ddib.product.product.dto.request.ProductStockDecreaseRequestDto;
import com.ddib.product.product.dto.request.ProductStockUpdateRequestDto;
import com.ddib.product.product.dto.response.ProductMainResponseDto;
import com.ddib.product.product.dto.response.ProductResponseDto;
import com.ddib.product.product.dto.response.ProductViewResponseDto;
import com.ddib.product.product.exception.ProductNotFoundException;
import com.ddib.product.product.repository.ProductRepository;
import com.ddib.product.product.repository.ProductRepositorySupport;
import com.ddib.product.user.domain.Seller;
import com.ddib.product.user.domain.User;
import com.ddib.product.user.exception.SellerNotFoundException;
import com.ddib.product.user.exception.UserNotFoundException;
import com.ddib.product.user.repository.SellerRepository;
import com.ddib.product.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.ddib.product.common.file.constant.S3Domain.PRODUCT_DETAIL;
import static com.ddib.product.common.file.constant.S3Domain.PRODUCT_THUMBNAIL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;

    private final ProductRepositorySupport productRepositorySupport;

    private final S3Uploader s3Uploader;

//    private final AlarmClient alarmClient;

    public void createProduct(List<MultipartFile> thumbnails, List<MultipartFile> details, ProductCreateRequestDto dto) {
        log.info("PRODUCT SERVICE : SAVE PRODUCT : {}", dto.getName());
        Seller seller = sellerRepository.findBySellerId(dto.getSellerId())
                .orElseThrow(SellerNotFoundException::new);

        List<String> detail = s3Uploader.storeImages(PRODUCT_DETAIL, details);
        List<String> thumbnail = s3Uploader.storeImages(PRODUCT_THUMBNAIL, thumbnails);

        Product product = dto.toEntity(seller);
        product.updateThumbnail(thumbnail.get(0));
        product.insertProductDetails(ProductDetail.of(detail, product));
        productRepository.save(product);
    }

    public ProductMainResponseDto getMainPageData() {
        log.info("PRODUCT SERVICE : get main page data");
        // 하루, 종료되지 않은 이벤트 데이터
        List<ProductResponseDto> todayNotOverProducts = productRepositorySupport.getTodayListNotOver()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        // 하루 데이터
        List<ProductResponseDto> todayProducts = productRepositorySupport.getTodayList()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        return ProductMainResponseDto.builder()
                .todayNotOverProducts(todayNotOverProducts)
                .todayProducts(todayProducts)
                .build();
    }

    public List<ProductResponseDto> findProductsByConditions(String keyword, String category) {
        log.info("PRODUCT SERVICE : SEARCH BY CONDITIONS : {} , {}", keyword, category);
        return productRepositorySupport.findByConditions(keyword, category)
                .stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    public void decreaseStock(ProductStockDecreaseRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        product.decreaseStock(dto.getAmount());
    }

    public void updateStock(ProductStockUpdateRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        product.updateStock(dto.getAmount());
    }

    public void likeProduct(ProductLikeRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        FavoriteProduct favoriteProduct = FavoriteProduct.builder()
                .product(product)
                .user(user)
                .build();

        product.getLikedUsers().add(favoriteProduct);
        user.getLikedProducts().add(favoriteProduct);
    }

    public List<ProductResponseDto> findFavoriteProductByUserId(int userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new)
                .getLikedProducts()
                .stream()
                .map(FavoriteProduct::getProduct)
                .map(ProductResponseDto::of)
                .toList();
    }

    public List<ProductResponseDto> findProductsBySellerId(int sellerId) {
        return sellerRepository.findBySellerId(sellerId)
                .orElseThrow(SellerNotFoundException::new)
                .getProducts()
                .stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    public List<List<ProductResponseDto>> findProductsInWeekend() {
        List<ProductResponseDto> dtos = productRepositorySupport.getWeekList()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        // list -> map 에서 다시 key값 안쓰는 배열로 변환해주기 -> list<list>
        Map<String, List<ProductResponseDto>> resultMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(LocalDate.now().toString().split("T")[0], formatter);
        for(int day = 0 ; day < 7 ; day++){
            String key = formatter.format(start.plusDays(day));
            resultMap.computeIfAbsent(key, k -> new ArrayList<>());
        }
        for (ProductResponseDto dto : dtos) {
            LocalDate startDate = LocalDate.parse(dto.getEventStartDate().toString().split("T")[0], formatter);
            String key = formatter.format(startDate);
            resultMap.get(key).add(dto);
        }

        return new ArrayList<>(resultMap.values());
    }

    public ProductViewResponseDto findProductByProductId(int productId, int userId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        boolean isLiked = user.getLikedProducts()
                .stream()
                .anyMatch(fp -> fp.getProduct().equals(product));
        return ProductViewResponseDto.of(product, isLiked);
    }

    public void cancelFavoriteProduct(int productId, int userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findByProductId(productId).orElseThrow(ProductNotFoundException::new);

        List<FavoriteProduct> fps =  user.getLikedProducts();
        for(FavoriteProduct fp : fps){
            if(fp.getProduct().equals(product)){
                user.getLikedProducts().remove(fp);
                product.getLikedUsers().remove(fp);
                break;
            }
        }
    }

}
