package com.shopping.cart.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotCreatedException extends RuntimeException {

    public ResourceNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
