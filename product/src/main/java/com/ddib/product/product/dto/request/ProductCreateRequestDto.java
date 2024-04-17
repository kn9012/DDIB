package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {

    private String name;

    private int totalStock;

    private Timestamp eventDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private ProductCategory category;

}
