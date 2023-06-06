package com.tuananhdo.security;

import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.User;
import com.tuananhdo.mapper.UserMapper;
import com.tuananhdo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        UserDTO userDTO = UserMapper.mapToUserDTO(user);
        if (userDTO != null) {
            if (userDTO.isEnabled()) {
                return new org.springframework.security.core.userdetails.User(
                        userDTO.getEmail(),
                        userDTO.getPassword(),
                        userDTO.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList()));
            } else {
                throw new UsernameNotFoundException("User is not enabled");
            }
        }
        throw new UsernameNotFoundException("Invalid username or password");

    }

}
