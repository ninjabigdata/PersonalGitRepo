package com.shopping.cart.app.controller.advice;

import com.shopping.cart.app.exception.InvalidDataException;
import com.shopping.cart.app.exception.ResourceNotCreatedException;
import com.shopping.cart.app.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ShoppingCartControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidInputArguments(MethodArgumentNotValidException exception) {
        log.error("Invalid input argument exception ", exception);

        return exception.getAllErrors().stream()
                .map(error -> ((FieldError)error))
                .collect(Collectors.toMap(
                        FieldError::getField, FieldError::getDefaultMessage
                ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleInvalidInputArgumentType(MethodArgumentTypeMismatchException exception) {
        log.error("Invalid input argument exception ", exception);

        Map<String, String> error = new HashMap<>();

        error.put(exception.getName(), "Invalid data format");

        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND, )
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleResourceNotFound(HttpClientErrorException.NotFound notFound, WebRequest webRequest) {
        return "Invalid URL";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleInternalResourceNotFound(ResourceNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceNotCreatedException.class)
    public String handleResourceNotCreated(ResourceNotCreatedException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataException.class)
    public String handleInvalidData(InvalidDataException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleResourceNotCreated(Exception exception) {
        log.error("Exception ", exception);
        return "Unexpected system error. Please contact administrator. ";
    }

}
