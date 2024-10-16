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
public abstract class User {

    // Instance variables
    private String email;
    private String password;

    // Constructors
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


    // Getters + Setters
    /**
     *
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Validates the email for correct formatting
     *
     * @return true if email is valid, false otherwise
     */
    public boolean emailIsValid() {
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Validates the password: <br>
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
