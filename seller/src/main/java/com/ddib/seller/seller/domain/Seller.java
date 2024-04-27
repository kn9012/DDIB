package com.ddib.seller.seller.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "판매회원")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "판매회원 식별키")
    private Integer sellerId;

    @Column(nullable = false)
    @Schema(description = "판매회원 이메일")
    private String sellerEmail;

    @Column(nullable = false)
    @Schema(description = "기업명")
    private String companyName;

    @Column(nullable = false)
    @Schema(description = "사업자 등록 번호")
    private int businessNumber;

    @Column(nullable = false)
    @Schema(description = "대표명")
    private String ceoName;

    @Column(nullable = false)
    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @Column(nullable = false)
    @Schema(description = "대표 전화번호")
    private String ceoPhone;
}
