package com.tuananhdo.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityUtils {

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public static String getRole() {
        User user = getCurrentUser();
        if (user != null) {
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                return authority.getAuthority();
            }
        }
        return null;
    }
}
