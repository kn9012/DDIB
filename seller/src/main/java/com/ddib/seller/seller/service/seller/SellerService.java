package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.dto.response.SellerInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface SellerService {
    void applySeller(SellerRequestDto requestDto, Integer sellerId);
    SellerInfoDto findSeller(Integer sellerId);
    void modifySeller(SellerModifyRequestDto requestDto, Integer sellerId);
    void deleteSeller(Integer sellerId);
}
