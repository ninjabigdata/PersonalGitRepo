package com.shopping.cart.app.service;

import com.shopping.cart.app.dto.ProductDTO;
import com.shopping.cart.app.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDTO create(ProductDTO productDTO);

    ProductDTO getById(Long productId);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByName(String name);

    List<ProductDTO> getProductsByCategory(String productCategory);

    ProductDTO update(Long productId, ProductDTO productDTO);

    void delete(Long productId);

    Product getProductById(Long productId);

    ProductDTO convert(Product product);

}
