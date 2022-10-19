package com.shopping.cart.app.service;

import com.shopping.cart.app.dto.CartDTO;
import com.shopping.cart.app.dto.CartProductDTO;
import com.shopping.cart.app.dto.ProductCategory;
import com.shopping.cart.app.entity.Cart;
import com.shopping.cart.app.entity.CartProduct;
import com.shopping.cart.app.entity.Product;
import com.shopping.cart.app.exception.InvalidDataException;
import com.shopping.cart.app.exception.ResourceNotFoundException;
import com.shopping.cart.app.repository.CartProductRepository;
import com.shopping.cart.app.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartProductRepository cartProductRepository;

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public CartDTO addProduct(Long userId, Long productId) {
        log.info("Add the product with product id - {} to the cart belonging to user with user id - {}",
                productId, userId);

        Cart requestedCart = this.getCartByUserId(userId);
        Product productToBeAdded = this.productService.getProductById(productId);

        Optional<CartProduct> requestedCartProduct = requestedCart.getCartProducts()
                .stream().filter(
                        cartProduct -> productToBeAdded.compareTo(cartProduct.getProduct()) == 0
                ).findAny();

        if (requestedCartProduct.isPresent()) {
            requestedCartProduct.get().setQuantity(requestedCartProduct.get().getQuantity() + 1);
        } else {
            CartProduct cartProduct = CartProduct.builder()
                    .product(productToBeAdded)
                    .quantity(1)
                    .cartId(requestedCart.getId())
                    .build();

            requestedCart.getCartProducts().add(cartProduct);
        }

        return convert(cartRepository.save(requestedCart));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public CartDTO removeProduct(Long userId, Long productId) {
        log.info("Remove the product with product id - {} to the cart belonging to user with user id - {}",
                productId, userId);

        Cart requestedCart = this.getCartByUserId(userId);
        Product productToBeRemoved = this.productService.getProductById(productId);
        CartProduct cartProductToBeRemoved = getCartProduct(productId, requestedCart, productToBeRemoved);

        requestedCart.getCartProducts().remove(cartProductToBeRemoved);

        cartProductRepository.delete(cartProductToBeRemoved);

        log.info("Cart details after removing the requested product id - {} is {}", productId, requestedCart);

        return convert(this.getCartByUserId(userId));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public CartDTO updateCart(Long userId, Long productId, Integer quantity) {
        log.info("Updating the quantity of product with product id - {} " +
                        "to the cart belonging to user with user id - {} to {}",
                productId, userId, quantity);

        Cart requestedCart = this.getCartByUserId(userId);
        Product productToBeUpdated = this.productService.getProductById(productId);
        CartProduct cartProductToBeUpdated = getCartProduct(productId, requestedCart, productToBeUpdated);

        if (quantity == 0) {
            requestedCart.getCartProducts().remove(cartProductToBeUpdated);

            cartProductRepository.delete(cartProductToBeUpdated);

            log.info("Cart details after updated the requested product id - {} is {}", productId, requestedCart);

            return convert(this.getCartByUserId(userId));
        } else {
            int updatedQuantity = Integer.sum(cartProductToBeUpdated.getQuantity(), quantity);

            log.info("Quantity to be updated for product id - {} is {}", productId, updatedQuantity);

            if (updatedQuantity == 0) {
                requestedCart.getCartProducts().remove(cartProductToBeUpdated);

                cartProductRepository.delete(cartProductToBeUpdated);

                log.info("Cart details after updated the requested product id - {} is {}", productId, requestedCart);

                return convert(this.getCartByUserId(userId));
            } else if (updatedQuantity > 0) {
                cartProductToBeUpdated.setQuantity(updatedQuantity);
            } else {
                throw new InvalidDataException(
                        "The quantity cannot be updated for the given product since new quantity falls below 0."
                );
            }
        }

        return convert(cartRepository.save(requestedCart));
    }

    private CartProduct getCartProduct(Long productId, Cart requestedCart, Product productToBeUpdated) {
        return requestedCart.getCartProducts()
                .stream()
                .filter(cartProduct -> productToBeUpdated.compareTo(cartProduct.getProduct()) == 0)
                .findAny()
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "The product with product id " + productId + " is not available in cart"
                        )
                );
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public CartDTO viewCart(Long userId) {
        log.info("Getting the cart belonging to user with user id - {}", userId);

        return convert(this.getCartByUserId(userId));
    }

    private Cart getCartByUserId(Long userId) {
        log.info("Getting cart details for user id - {}", userId);

        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("No user available for the given user Id " + userId)
        );
    }

    private CartDTO convert(Cart cart) {
        log.info("Converting cart entity - {} to cart DTO", cart);

        CartDTO cartDTO = CartDTO.builder()
                .user(userService.convert(cart.getUser()))
                .totalPrice(0.0)
                .build();

        List<CartProductDTO> cartProductDTOS = cart.getCartProducts()
                .stream()
                .map(
                        cartProduct -> {
                            CartProductDTO cartProductDTO = CartProductDTO.builder()
                                    .productId(cartProduct.getProduct().getProductId())
                                    .productCategory(
                                            cartProduct.getProduct().getBook() == null
                                                    ? ProductCategory.APPAREL : ProductCategory.BOOK
                                    )
                                    .productName(cartProduct.getProduct().getProductName())
                                    .productPrice(cartProduct.getProduct().getPrice())
                                    .quantity(cartProduct.getQuantity())
                                    .build();

                            cartDTO.setTotalPrice(
                                    Double.sum(
                                            cartDTO.getTotalPrice(), (
                                                    cartProduct.getProduct().getPrice() * cartProduct.getQuantity()
                                            )
                                    )
                            );

                            return cartProductDTO;
                        }
                )
                .collect(Collectors.toList());

        cartDTO.setProducts(cartProductDTOS);

        return cartDTO;
    }

}
