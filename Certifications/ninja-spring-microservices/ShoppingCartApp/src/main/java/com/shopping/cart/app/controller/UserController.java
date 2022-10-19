package com.shopping.cart.app.controller;

import com.shopping.cart.app.dto.UserDTO;
import com.shopping.cart.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Gets a user by userId")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@Valid @Pattern(regexp = "^[0-9]+$") @PathVariable("id") Long id) {
        log.info("Get user details for user id - {}", id);

        return userService.findUserById(id);
    }

    @Operation(summary = "Gets all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        log.info("Get all user details");

        return userService.findAllUsers();
    }

    @Operation(summary = "Creates a user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Create user for - {}", userDTO);

        return userService.createUser(userDTO);
    }

    @Operation(summary = "Updates the user details")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@Valid @Pattern(regexp = "^[0-9]+$")@PathVariable("id") Long id,
                              @Valid @RequestBody UserDTO userDTO) {
        log.info("Update user details for user id - {} with details - {}", id, userDTO);

        return userService.updateUser(id, userDTO);
    }

    @Operation(summary = "Deletes the user by userId")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@Valid @Pattern(regexp = "^[0-9]+$")@PathVariable("id") Long id) {
        log.info("Delete user details for user id - {}", id);

        userService.deleteUser(id);
    }

}
