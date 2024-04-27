package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.domain.Seller;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Transactional
    @Override
    public void applySeller(SellerRequestDto requestDto, Principal principal) {
        sellerRepository.findBySellerEmail(principal.getName()).updateSeller(requestDto);
    }
}
