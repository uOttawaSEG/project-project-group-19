package com.example.eams.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class User {

    private String email;
    private String password;

    // DO NOT REMOVE: Required for Firebase!!
    public User() {

    }

    /**
     * Creates a new user with the given email and password
     *
     * @param email    the user's email as a String
     * @param password the
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validates the email
     *
     * @return true if email is valid, false otherwise
     */
    public boolean emailIsValid() {
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Validates the password <br>
     * password must have 1 lowercase letter,
     * 1 uppercase letter, 1 number, one special character, min length of 8 characters
     *
     * @return true if password is valid, false otherwise
     */
    public boolean passwordIsValid() {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

}
