package com.example.eams.users;

/**
 * Admin refers to an Administrator user, with a provided login (email & password)
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Admin extends User {

    /**
     * Parameterized constructor that creates an Admin with given email and password
     * Calls constructor of superclass User
     *
     * @param email
     * @param password
     */
    public Admin(String email, String password) {
        super(email, password);
    }
}
