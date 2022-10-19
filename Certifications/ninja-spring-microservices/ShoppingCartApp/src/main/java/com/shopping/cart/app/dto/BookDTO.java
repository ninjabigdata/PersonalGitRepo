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
public class BookDTO extends ProductDTO {

    @NotBlank(message = "Book genre is mandatory")
    @Length(min = 3, message = "Book genre must have minimum 3 characters")
    private String genre;

    @NotBlank(message = "Book author is mandatory")
    @Length(min = 3, message = "Book author must have minimum 3 characters")
    private String author;

    @NotBlank(message = "Book publication is mandatory")
    @Length(min = 3, message = "Book publication must have minimum 3 characters")
    private String publications;

}
