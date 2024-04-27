package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.domain.Seller;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Override
    public void applySeller(SellerRequestDto requestDto, Principal principal) {
        Seller seller = Seller.builder()
                .companyName(requestDto.getCompanyName())
                .businessNumber(requestDto.getBusinessNumber())
                .ceoName(requestDto.getCeoName())
                .ceoEmail(requestDto.getCeoEmail())
                .ceoPhone(requestDto.getCeoPhone())
                .build();
        sellerRepository.save(seller);
    }
}
