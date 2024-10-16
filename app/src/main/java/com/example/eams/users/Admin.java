package com.example.eams.users;

/**
 * Admin refers to an Administrator user, with a provided login (email & password)
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Admin extends User {

    // DO NOT REMOVE: Required for Firebase!!
    public Admin() {

    }

    /**
     * Creates an Admin with given email and password
     *
     * @param email    email to set
     * @param password password to set
     */
    public Admin(String email, String password) {
        super(email, password);
    }
}
