package com.tuananhdo.service;

import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.EmailDuplicatedException;
import com.tuananhdo.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    List<UserDTO> listAllActiveUsers();

    List<Role> listRoles();

    User saveUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO) throws UserNotFoundException, EmailDuplicatedException;

    void markUserAsDeleted(Long userId);

    UserDTO findByUserId(Long id);

    UserDTO findByUsername(String username);

    UserDTO getUserByEmail(String email);

}
