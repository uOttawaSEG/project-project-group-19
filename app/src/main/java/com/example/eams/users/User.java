package com.example.eams.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Validates the email
     *
     * @return true if email is valid, false otherwise
     */
    public boolean validateEmail() {
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Validates the password
     *
     * @return true if password is valid, false otherwise
     * password must have 1 lowercase letter, 1 uppercase letter, 1 number, one special character, min length of 8 characters
     */
    public boolean validatePassword() {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

}
