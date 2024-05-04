package com.ddib.seller.seller.service.seller;

import com.ddib.seller.seller.domain.Seller;
import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import com.ddib.seller.seller.dto.response.SellerInfoDto;
import com.ddib.seller.seller.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Transactional
    @Override
    public void applySeller(SellerRequestDto requestDto, String email) {
        sellerRepository.findBySellerEmail(email).applySeller(requestDto);
    }

    @Override
    public SellerInfoDto findSeller(String email) {
        Seller seller = sellerRepository.findBySellerEmail(email);
        return SellerInfoDto.builder()
                .sellerId(seller.getSellerId())
                .companyName(seller.getCompanyName())
                .businessNumber(seller.getBusinessNumber())
                .ceoName(seller.getCeoName())
                .ceoEmail(seller.getCeoEmail())
                .ceoPhone(seller.getCeoPhone())
                .build();
    }

    @Transactional
    @Override
    public void modifySeller(SellerModifyRequestDto requestDto, String email) {
        sellerRepository.findBySellerEmail(email).updateSeller(requestDto);
    }

    @Transactional
    @Override
    public void deleteSeller(String email) {
        sellerRepository.deleteBySellerEmail(email);
    }
}
