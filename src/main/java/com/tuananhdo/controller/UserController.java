package com.tuananhdo.controller;

import com.tuananhdo.dto.UserDTO;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.EmailDuplicatedException;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.service.FileUploadService;
import com.tuananhdo.service.UserAuthenticationService;
import com.tuananhdo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final UserAuthenticationService authenticationService;
    private final FileUploadService fileUploadService;

    public UserController(UserService userService, UserAuthenticationService authenticationService, FileUploadService fileUploadService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/admin/users")
    public String listAllUsers(Model model) {
        List<UserDTO> users = userService.listAllActiveUsers();
        model.addAttribute("users", users);
        return "admin/user/user-home";
    }

    @GetMapping("/admin/user/create")
    public String createUser(Model model) {
        List<Role> listRoles = userService.listRoles();
        UserDTO user = new UserDTO();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("user", user);
        return "admin/user/create-user";
    }

    @GetMapping("/admin/user/update/{userId}")
    public String updateUser(@PathVariable("userId") Long userId, Model model) {
        List<Role> listRoles = userService.listRoles();
        UserDTO user = userService.findByUserId(userId);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("user", user);
        return "admin/user/update-user";
    }


    @PostMapping("/admin/save/user/{userId}")
    public String updateUser(@PathVariable("userId") Long userId,
                             @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result,
                             @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException, UserNotFoundException, EmailDuplicatedException {
        UserDTO user = userService.findByUserId(userDTO.getId());
        if (!userDTO.getEmail().equals(user.getEmail())){
            if (existingUser(userDTO,result,model)) return "admin/user/update-user";
        }
        if (!multipartFile.isEmpty()) {
            fileUploadService.saveUserWithFile(userDTO, multipartFile);
        } else {
            fileUploadService.cleanUploadDir(userId);
            userService.updateUser(userDTO);
        }
        return "redirect:/admin/users";
    }


    private boolean existingUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model) {
        User existingUser = authenticationService.findByEmail(userDTO.getEmail());
            if (existingUser != null && !existingUser.getEmail().isEmpty()) {
                result.rejectValue("email", null, "There is already a user with same email");
        }
        List<Role> listRoles = userService.listRoles();
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("listRoles", listRoles);
            return true;
        }
        return false;
    }


    @PostMapping("/admin/save/user")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result,
                           @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        if (existingUser(userDTO, result, model)) return "admin/user/create-user";
        if (!multipartFile.isEmpty()) {
            fileUploadService.saveUserWithFile(userDTO, multipartFile);
        }else {
            userService.saveUser(userDTO);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) throws IOException {
        fileUploadService.cleanUploadDir(userId);
        userService.markUserAsDeleted(userId);
        return "redirect:/admin/users";
    }
}
