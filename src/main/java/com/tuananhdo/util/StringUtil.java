package com.tuananhdo.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    public static String truncateString(String input, int maxLength) {
        if (input.length() > maxLength) {
            return input.substring(0, maxLength) + "...";
        } else {
            return input;
        }
    }
}
