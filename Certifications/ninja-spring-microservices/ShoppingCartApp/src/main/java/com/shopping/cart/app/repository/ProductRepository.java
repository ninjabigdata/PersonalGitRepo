package com.shopping.cart.app.repository;

import com.shopping.cart.app.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByProductName(String productName);

    List<Product> findAllBooks();

    List<Product> findAllApparels();

}
