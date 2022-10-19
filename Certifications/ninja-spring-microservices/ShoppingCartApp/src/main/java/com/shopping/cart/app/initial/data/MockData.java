package com.shopping.cart.app.initial.data;

import com.shopping.cart.app.entity.*;
import com.shopping.cart.app.repository.CartRepository;
import com.shopping.cart.app.repository.ProductRepository;
import com.shopping.cart.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = addUser();
        Product book = addBook();
        Product apparel = addApparel();

        updateCart(user, book, 10);
        updateCart(user, apparel, 1);
    }

    private User addUser() {
        Cart cart = new Cart();

        User user = User.builder()
                .cart(cart)
                .name("Test User")
                .mobileNumber("9977884411")
                .emailId("testuser@gmail.com")
                .build();

        cart.setUser(user);

        return userRepository.save(user);
    }

    private Product addBook() {
        Book book = Book.builder()
                .publications("Pearson")
                .genre("Programming")
                .author("Robert C Martin")
                .build();

        Product product = Product.builder()
                .price(450.0)
                .productName("The Clean Coder")
                .book(book)
                .build();

        book.setProduct(product);

        return productRepository.save(product);
    }

    private Product addApparel() {
        Apparel apparel = Apparel.builder()
                .type("Chino")
                .design("Strips")
                .brand("Louis Vuitton")
                .build();

        Product product = Product.builder()
                .price(650.0)
                .productName("Green Chino")
                .apparel(apparel)
                .build();

        apparel.setProduct(product);

        return productRepository.save(product);
    }

    private void updateCart(User user, Product product, int quantity) {
        Cart cart = cartRepository.findById(user.getId()).get();

        CartProduct cartProduct = CartProduct.builder()
                .cartId(cart.getId())
                .quantity(quantity)
                .product(product)
                .build();

        cart.getCartProducts().add(cartProduct);

        cartRepository.save(cart);
    }

}
