package com.shopping.cart.app.repository;

import com.shopping.cart.app.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
