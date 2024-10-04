package com.example.eams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LoginUser {
    private String email;
    private String password;

    /**
     * Validates the email
     * @return true if email is valid, false otherwise
     */
    public boolean validateEmail() {
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Validates the password
     * @return true if password is valid, false otherwise
     */
    public boolean validatePassword() {
       return true;
    }

    public static boolean validateLogin(String email, String password) {
        return true;
    }
}
