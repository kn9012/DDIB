package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.dto.response.SellerInfoDto;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface SellerService {
    void applySeller(SellerRequestDto requestDto, Principal principal);
    SellerInfoDto findSeller(Principal principal);
    void modifySeller(SellerModifyRequestDto requestDto, Principal principal);
    void deleteSeller(Principal principal);
}
