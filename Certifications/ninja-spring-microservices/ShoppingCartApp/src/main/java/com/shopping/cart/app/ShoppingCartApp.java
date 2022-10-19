package com.shopping.cart.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(info = @Info(title = "Shopping Cart App"))
public class ShoppingCartApp {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApp.class, args);
    }


}
