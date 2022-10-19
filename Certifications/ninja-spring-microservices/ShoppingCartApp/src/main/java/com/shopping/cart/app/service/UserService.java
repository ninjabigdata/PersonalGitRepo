package com.shopping.cart.app.service;

import com.shopping.cart.app.dto.UserDTO;
import com.shopping.cart.app.entity.User;

import java.util.List;

public interface UserService {

    UserDTO findUserById(Long id);

    List<UserDTO> findAllUsers();

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO convert(User user);

}
