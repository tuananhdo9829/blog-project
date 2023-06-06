package com.tuananhdo.mapper;

import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.User;

public class UserMapper {

    //convert User to UserDTO
    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .photos(user.getPhotos())
                .deleteStatus(user.getDeleteStatus())
                .deletedAt(user.getDeleteAt())
                .authenticationType(user.getAuthenticationType())
                .roles(user.getRoles())
                .build();
    }

    //convert UserDTO to User
    public static User maptoUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .enabled(userDTO.isEnabled())
                .photos(userDTO.getPhotos())
                .deleteStatus(userDTO.getDeleteStatus())
                .deleteAt(userDTO.getDeletedAt())
                .authenticationType(userDTO.getAuthenticationType())
                .build();
    }
}
