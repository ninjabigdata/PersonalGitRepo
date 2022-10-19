package com.shopping.cart.app.service;

import com.shopping.cart.app.dto.UserDTO;
import com.shopping.cart.app.entity.Cart;
import com.shopping.cart.app.entity.User;
import com.shopping.cart.app.exception.ResourceNotFoundException;
import com.shopping.cart.app.repository.UserRepository;
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
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public UserDTO findUserById(Long id) {
        log.info("Get user details for userId - {}", id);

        return convert(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found for id - " + id))
        );
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, timeout = 100)
    public List<UserDTO> findAllUsers() {
        log.info("Get all user details");

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(timeout = 100)
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Add user details - {}", userDTO);

        return convert(userRepository.save(convert(userDTO)));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Update user details for userId - {} with details - {}", id, userDTO);

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found for user id - " + id));

        user.setName(userDTO.getName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setEmailId(userDTO.getEmailId());

        return convert(userRepository.save(user));
    }

    @Override
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 100)
    public void deleteUser(Long id) {
        log.info("Delete user details for userId - {}", id);

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found for user id - " + id));

        userRepository.delete(user);
    }

    public UserDTO convert(User user) {
        log.info("Converting user entity - {} to user DTO", user);

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .mobileNumber(user.getMobileNumber())
                .emailId(user.getEmailId())
                .build();
    }

    private User convert(UserDTO userDTO) {
        log.info("Converting user DTO - {} to user entity", userDTO);

        Cart cart = new Cart();

        User user = User.builder()
                .name(userDTO.getName())
                .mobileNumber(userDTO.getMobileNumber())
                .emailId(userDTO.getEmailId())
                .cart(cart)
                .build();

        cart.setUser(user);

        return user;
    }
}
