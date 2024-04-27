package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.dto.request.SellerRequestDto;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface SellerService {
    void applySeller(SellerRequestDto requestDto, Principal principal);
}
