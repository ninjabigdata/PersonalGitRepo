package com.shopping.cart.app.repository;

import com.shopping.cart.app.entity.CartProduct;
import org.springframework.data.repository.CrudRepository;

public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
}
