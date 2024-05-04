package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.dto.response.SellerInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface SellerService {
    void applySeller(SellerRequestDto requestDto, String email);
    SellerInfoDto findSeller(String email);
    void modifySeller(SellerModifyRequestDto requestDto, String email);
    void deleteSeller(String email);
}
