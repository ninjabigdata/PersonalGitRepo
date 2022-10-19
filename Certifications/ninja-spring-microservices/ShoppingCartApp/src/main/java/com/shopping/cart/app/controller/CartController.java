package com.shopping.cart.app.controller;

import com.shopping.cart.app.dto.CartDTO;
import com.shopping.cart.app.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Gets the cart by userId")
    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO getCartByUserId(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("userId") Long userId) {
        log.info("Getting cart for user id - {}", userId);

        return cartService.viewCart(userId);
    }

    @Operation(summary = "Add the product by product id to cart belonging to user id")
    @PutMapping("{userId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO addProduct(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("userId") Long userId,
                              @Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId) {
        log.info("Adding the product with product id to the cart belonging to user with user id - {}",
                productId, userId);

        return cartService.addProduct(userId, productId);
    }

    @Operation(summary = "Remove the product by product id from cart belonging to user id")
    @DeleteMapping("{userId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO removeProduct(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("userId") Long userId,
                              @Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId) {
        log.info("Removing the product with product id from the cart belonging to user with user id - {}",
                productId, userId);

        return cartService.removeProduct(userId, productId);
    }

    @Operation(summary = "Update the quantity of product by product id from cart belonging to user id")
    @PutMapping("{userId}/{productId}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO updateProductQuantity(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("userId") Long userId,
                                         @Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("productId") Long productId,
                                         @PathVariable("quantity") Integer quantity) {
        log.info("Updating the quantity of product with product id from the cart belonging to user with user id - {}",
                productId, userId);

        return cartService.updateCart(userId, productId, quantity);
    }

}
