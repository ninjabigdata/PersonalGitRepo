package com.shopping.cart.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductDTO {

    private Long productId;
    private ProductCategory productCategory;
    private String productName;
    private Double productPrice;
    private Integer quantity;

}
