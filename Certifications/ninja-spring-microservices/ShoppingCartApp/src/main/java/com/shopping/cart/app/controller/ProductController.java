package com.shopping.cart.app.controller;

import com.shopping.cart.app.dto.ApparelDTO;
import com.shopping.cart.app.dto.BookDTO;
import com.shopping.cart.app.dto.ProductDTO;
import com.shopping.cart.app.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Creates a book")
    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info("Creating a book for - {}", bookDTO);

        return (BookDTO) productService.create(bookDTO);
    }

    @Operation(summary = "Creates an apparel")
    @PostMapping("apparel")
    @ResponseStatus(HttpStatus.CREATED)
    public ApparelDTO addApparel(@Valid @RequestBody ApparelDTO apparelDTO) {
        log.info("Creating a apparel for - {}", apparelDTO);

        return (ApparelDTO) productService.create(apparelDTO);
    }

    @Operation(summary = "Gets a product by productId")
    @GetMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId) {
        log.info("Getting product details for productId - {}", productId);

        return productService.getById(productId);
    }

    @Operation(summary = "Gets all products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProducts() {
        log.info("Getting all product details");

        return productService.getAllProducts();
    }

    @Operation(summary = "Gets all products by name")
    @GetMapping("{productName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProductsByName(@Valid @NotBlank(message = "Product name is mandatory")
                                                     @Length(min = 3, message = "Product name must be of minimum 3 characters")
                                                     @PathVariable("productName") String productName) {
        log.info("Getting product details for product name - {}", productName);

        return productService.getProductsByName(productName);
    }

    @Operation(summary = "Gets all products by category")
    @GetMapping("category/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAllProductsByCategory(@PathVariable("categoryName") String categoryName) {
        log.info("Getting product details for product category - {}", categoryName);

        return productService.getProductsByCategory(categoryName);
    }

    @Operation(summary = "Updates the book details")
    @PutMapping("book/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateBook(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId,
                                 @Valid @RequestBody BookDTO bookDTO) {
        log.info("Update book details for productId - {} with details - {}", productId, bookDTO);

        return productService.update(productId, bookDTO);
    }

    @Operation(summary = "Updates the apparel details")
    @PutMapping("apparel/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateApparel(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId,
                                    @Valid @RequestBody ApparelDTO apparelDTO) {
        log.info("Getting apparel details for productId - {} with details - {}", productId, apparelDTO);

        return productService.update(productId, apparelDTO);
    }

    @Operation(summary = "Deletes the product by productId")
    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@Valid @Pattern(regexp = "^[0-9]+$")@PathVariable("productId") Long productId) {
        log.info("Delete product details for productId - {}", productId);

        productService.delete(productId);
    }

}
