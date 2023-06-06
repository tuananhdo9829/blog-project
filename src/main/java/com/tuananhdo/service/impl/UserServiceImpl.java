package com.tuananhdo.service.impl;

import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.EmailDuplicatedException;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.mapper.UserMapper;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.repository.UserRepository;
import com.tuananhdo.service.UserService;
import com.tuananhdo.util.AuthenticationType;
import com.tuananhdo.util.DeleteStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> listUsers = userRepository.findAll();
        return listUsers.stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listAllActiveUsers() {
        List<User> activeUsers = userRepository.findAllActiveUsers(DeleteStatus.ACTIVE);
        return activeUsers.stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Cannot find with user: " + username);
//        }
        return null;
    }

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = UserMapper.maptoUser(userDTO);
        user.setEnabled(true);
        user.setAuthenticationType(AuthenticationType.DATABASE);
        user.setDeleteStatus(DeleteStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = userDTO.getRoles()
                .stream()
                .map(role -> roleRepository.findById(role.getId()).orElseThrow(() -> new IllegalArgumentException("Role not found")))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) throws UserNotFoundException, EmailDuplicatedException {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserNotFoundException("User not found "));
        user.setEnabled(userDTO.isEnabled());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
        Set<Role> roles = userDTO.getRoles()
                .stream()
                .map(role -> roleRepository.findById(role.getId()).orElseThrow(() -> new IllegalArgumentException("Role not found")))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void markUserAsDeleted(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setDeleteStatus(DeleteStatus.DELETED);
            user.setEnabled(false);
            user.setDeleteAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    @Override
    public UserDTO findByUserId(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::mapToUserDTO)
                .orElse(null);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.mapToUserDTO(user);
    }
}
