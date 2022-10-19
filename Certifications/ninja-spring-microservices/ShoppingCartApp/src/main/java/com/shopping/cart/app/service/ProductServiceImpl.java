package com.shopping.cart.app.service;

import com.shopping.cart.app.dto.ApparelDTO;
import com.shopping.cart.app.dto.BookDTO;
import com.shopping.cart.app.dto.ProductCategory;
import com.shopping.cart.app.dto.ProductDTO;
import com.shopping.cart.app.entity.Apparel;
import com.shopping.cart.app.entity.Book;
import com.shopping.cart.app.entity.Product;
import com.shopping.cart.app.exception.InvalidDataException;
import com.shopping.cart.app.exception.ResourceNotFoundException;
import com.shopping.cart.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(timeout = 100)
    public ProductDTO create(ProductDTO productDTO) {
        log.info("Creating a product for - {}", productDTO);

        return convert(productRepository.save(convert(productDTO)));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public ProductDTO getById(Long productId) {
        log.info("Get a product by productId - {}", productId);

        return convert(this.getProductById(productId));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public List<ProductDTO> getAllProducts() {
        log.info("Get all product details");

        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public List<ProductDTO> getProductsByName(String name) {
        log.info("Get product details for the product name - {}", name);

        return productRepository.findByProductName(name)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public List<ProductDTO> getProductsByCategory(String productCategory) {
        log.info("Get product details for the product category - {}", productCategory);

        ProductCategory requestedProductCategory;

        try {
            requestedProductCategory = ProductCategory.valueOf(productCategory.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidDataException("The value of the category must be either book or apparel", exception);
        }

        return (
                requestedProductCategory.equals(ProductCategory.BOOK)
                        ? productRepository.findAllBooks() : productRepository.findAllApparels())
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        log.info("Update product details for the product id - {} with details - {}", productId, productDTO);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found for id - " + productId)
        );

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());

        if (productDTO instanceof BookDTO) {
            BookDTO bookDTO = (BookDTO) productDTO;

            Book book = product.getBook();
            book.setAuthor(bookDTO.getAuthor());
            book.setGenre(bookDTO.getGenre());
            book.setPublications(bookDTO.getPublications());
        } else {
            ApparelDTO apparelDTO = (ApparelDTO) productDTO;

            Apparel apparel = product.getApparel();
            apparel.setBrand(apparelDTO.getBrand());
            apparel.setDesign(apparelDTO.getDesign());
            apparel.setType(apparelDTO.getType());
        }

        return convert(productRepository.save(product));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public void delete(Long productId) {
        log.info("Delete product details for the product id - {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found for id - " + productId)
        );

        productRepository.delete(product);
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public Product getProductById(Long productId) {
        log.info("Get a product by productId - {}", productId);

        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product not found for id - " + productId)
        );
    }

    private Product convert(ProductDTO productDTO) {
        log.info("Converting product DTO - {} to product entity", productDTO);

        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .build();

        if (productDTO instanceof BookDTO) {
            BookDTO bookDTO = (BookDTO) productDTO;
            Book book = Book.builder()
                    .product(product)
                    .author(bookDTO.getAuthor())
                    .genre(bookDTO.getGenre())
                    .publications(bookDTO.getPublications())
                    .build();

            product.setBook(book);
        } else {
            ApparelDTO apparelDTO = (ApparelDTO) productDTO;
            Apparel apparel = Apparel.builder()
                    .product(product)
                    .brand(apparelDTO.getBrand())
                    .design(apparelDTO.getDesign())
                    .type(apparelDTO.getType())
                    .build();

            product.setApparel(apparel);
        }

        return product;
    }

    public ProductDTO convert(Product product) {
        log.info("Converting product entity - {} to product DTO", product);

        ProductDTO productDTO;

        if (product.getBook() == null) {
            productDTO = ApparelDTO.builder()
                    .brand(product.getApparel().getBrand())
                    .design(product.getApparel().getDesign())
                    .type(product.getApparel().getType())
                    .build();
        } else {
            productDTO = BookDTO.builder()
                    .author(product.getBook().getAuthor())
                    .genre(product.getBook().getGenre())
                    .publications(product.getBook().getPublications())
                    .build();
        }
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());

        return productDTO;
    }

}
