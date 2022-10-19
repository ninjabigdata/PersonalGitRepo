package com.shopping.cart.app.service;


import com.shopping.cart.app.dto.CartDTO;

public interface CartService {

    CartDTO addProduct(Long userId, Long productId);

    CartDTO removeProduct(Long userId, Long productId);

    CartDTO updateCart(Long userId, Long productId, Integer quantity);

    CartDTO viewCart(Long userId);

}
