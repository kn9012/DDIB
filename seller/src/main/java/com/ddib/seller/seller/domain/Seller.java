package com.ddib.seller.seller.domain;

import com.ddib.seller.seller.dto.request.SellerModifyRequestDto;
import com.ddib.seller.seller.dto.request.SellerRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매회원")
@ToString
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "판매회원 식별키")
    private Integer sellerId;

    @Column(nullable = false)
    @Schema(description = "판매회원 이메일")
    private String sellerEmail;

    @Schema(description = "기업명")
    private String companyName;

    @Schema(description = "사업자 등록 번호")
    private String businessNumber;

    @Column(nullable = false)
    @Schema(description = "대표명")
    private String ceoName;

    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @Schema(description = "대표 전화번호")
    private String ceoPhone;

    public void applySeller(SellerRequestDto requestDto) {
        this.companyName = requestDto.getCompanyName();
        this.businessNumber = requestDto.getBusinessNumber();
        this.ceoName = requestDto.getCeoName();
        this.ceoEmail = requestDto.getCeoEmail();
        this.ceoPhone = requestDto.getCeoPhone();
    }

    public void updateSeller(SellerModifyRequestDto requestDto) {
        this.companyName = requestDto.getCompanyName();
        this.businessNumber = requestDto.getBusinessNumber();
        this.ceoName = requestDto.getCeoName();
        this.ceoEmail = requestDto.getCeoEmail();
        this.ceoPhone = requestDto.getCeoPhone();
    }
}
