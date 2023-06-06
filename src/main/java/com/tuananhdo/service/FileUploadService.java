package com.tuananhdo.service;

import com.tuananhdo.dto.PostDTO;
import com.tuananhdo.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    void saveUserWithFile(UserDTO userDTO, MultipartFile multipartFile) throws IOException;

    void savePostWithFile(PostDTO postDTO, MultipartFile multipartFile) throws IOException;

    void cleanUploadDir(Long id) throws IOException;
}
