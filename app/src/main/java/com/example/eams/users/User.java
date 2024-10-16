package com.example.eams.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User contains instance variables and methods common to all users of the app.
 * It is extended by RegisterUser (and Organizer and Attendee) and Admin.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class User {

    // INSTANCE VARIABLES
    private String email;
    private String password;

    // CONSTRUCTOR(S)

    /**
     * Parameterized constructor that creates a User with given email and password.
     *
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // PUBLIC METHODS

    /**
     * Retrieves User's email as a String.
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets User's email to given email.
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Validates the email for correct formatting
     *
     * @return true if email is valid, false otherwise
     */
    public boolean validateEmail() {
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Retrieves the User's password as a String.
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets User's password to given password.
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validates the password: must have 1 lowercase letter, 1 uppercase letter, 1 number, one special character, min length of 8 characters
     *
     * @return true if password is valid, false otherwise
     */
    public boolean validatePassword() {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

}
