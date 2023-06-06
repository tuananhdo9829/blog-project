package com.tuananhdo.service.impl;

import com.tuananhdo.dto.PostDTO;
import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.Post;
import com.tuananhdo.entity.User;
import com.tuananhdo.service.FileUploadService;
import com.tuananhdo.service.PostService;
import com.tuananhdo.service.UserService;
import com.tuananhdo.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Objects;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    public static final String POST_PHOTOS = "post-photos/";
    public static final String USER_PHOTOS = "user-photos/";

    private final UserService userService;
    private final PostService postService;

    public FileUploadServiceImpl(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void saveUserWithFile(UserDTO userDTO, MultipartFile multipartFile) throws IOException {
        String fileName = getFileName(multipartFile);
        userDTO.setPhotos(fileName);
        User user = userService.saveUser(userDTO);
        String uploadDir = USER_PHOTOS + user.getId();
        saveFile(multipartFile, fileName, uploadDir);
    }

    @Override
    public void savePostWithFile(PostDTO postDTO, MultipartFile multipartFile) throws IOException {
        String fileName = getFileName(multipartFile);
        postDTO.setPhotos(fileName);
        postDTO.setUrl(getUrl(postDTO.getTitle()));
        Post post = postService.savePost(postDTO);
        String uploadDir = POST_PHOTOS + post.getId();
        saveFile(multipartFile, fileName, uploadDir);
    }


    @Override
    public void cleanUploadDir(Long id) throws IOException {
        String uploadDir;
        if (id != null) {
            uploadDir = POST_PHOTOS + id;
        } else {
            uploadDir = USER_PHOTOS + id;
        }
        FileUploadUtil.cleanDir(uploadDir);
    }

    private void saveFile(MultipartFile multipartFile, String fileName, String uploadDir) throws IOException {
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    private String getFileName(MultipartFile multipartFile) {
        return StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    }

    public static String getUrl(String postTitle) {
        String title = postTitle.toLowerCase();
        title = Normalizer.normalize(title, Normalizer.Form.NFD);
        title = title.trim();
        title = title.replaceAll("[^\\p{L}\\p{N}\\s-]", "");
        title = title.replaceAll("\\s+", "-");
        title = title.replaceAll("đ", "d");
        return title;
    }
}
