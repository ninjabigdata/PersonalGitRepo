package com.shopping.cart.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank(message = "Product name is mandatory")
    @Length(min = 3, message = "Product name must have minimum 3 characters")
    private String productName;

    @NotNull(message = "Product price is mandatory")
    @Positive(message = "Product price must be greater than zero")
    private Double price;

}
