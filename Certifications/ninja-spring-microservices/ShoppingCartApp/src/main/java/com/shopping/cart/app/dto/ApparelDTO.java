package com.shopping.cart.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApparelDTO extends ProductDTO {

    @NotBlank(message = "Apparel type is mandatory")
    @Length(min = 3, message = "Apparel type must have minimum 3 characters")
    private String type;

    @NotBlank(message = "Apparel brand is mandatory")
    @Length(min = 3, message = "Apparel brand must have minimum 3 characters")
    private String brand;

    @NotBlank(message = "Apparel design is mandatory")
    @Length(min = 3, message = "Apparel design must have minimum 3 characters")
    private String design;

}
