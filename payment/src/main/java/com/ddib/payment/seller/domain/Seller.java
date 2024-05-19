package com.ddib.payment.seller.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Schema(description = "판매회원 식별키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerId;

    @Schema(description = "기업명")
    @NotNull
    private String companyName;

}
