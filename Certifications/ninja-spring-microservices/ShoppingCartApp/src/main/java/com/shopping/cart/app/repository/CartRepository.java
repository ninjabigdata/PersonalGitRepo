package com.shopping.cart.app.repository;

import com.shopping.cart.app.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

}
