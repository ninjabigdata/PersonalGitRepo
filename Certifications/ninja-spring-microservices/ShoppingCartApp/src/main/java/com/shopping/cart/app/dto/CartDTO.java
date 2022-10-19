package com.shopping.cart.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    private List<CartProductDTO> products;
    private Double totalPrice;
    private UserDTO user;

}
