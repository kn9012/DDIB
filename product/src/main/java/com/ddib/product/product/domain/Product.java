package com.ddib.product.product.domain;

import com.ddib.product.product.exception.ProductStockShortageException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String name;

    private int totalStock;

    private int stock;

    private Timestamp eventDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private String thumbnailImage;

    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details;

    private boolean isOver;

    public void decreaseStock(int amount){
        isStockShortage(amount);
        stock -= amount;
    }

    public void updateStock(int amount) {
        this.stock = amount;
    }

    private void isStockShortage(int amount){
        if(stock - amount < 0){
            throw new ProductStockShortageException();
        }
    }
}
