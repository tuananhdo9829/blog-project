package com.tuananhdo.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encode {

        public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String name = "iamclairedoris@gmail.com";
        String pw = encoder.encode("xxxxxxx");
        System.out.println(pw);
    }
}
