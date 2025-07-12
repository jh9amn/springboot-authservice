package org.example.utils;

import java.util.regex.Pattern;

public class ValidateUserDetails {
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    private static final String EMAIL_REGEX =
            "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static  final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

}
