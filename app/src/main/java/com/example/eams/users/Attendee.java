package com.example.eams.users;

/**
 * Attendee extends RegisterUser (and User). They can login in MainActivity or
 *      register in AttendeeRegisterActivity.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Attendee extends RegisterUser {

    @Override
    public boolean register() {
        return false;
    }

    /**
     * Parameterized constructor that creates a new Attendee user with given register information.
     * Calls constructor of superclass RegisterUser.
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param streetAddress
     * @param city
     * @param province
     * @param postalCode
     */
    public  Attendee(String email, String password, String firstName, String lastName, String phoneNumber, String streetAddress, String city, String province, String postalCode) {
        super(email, password, firstName, lastName, phoneNumber, streetAddress, city, province, postalCode);
    }
}


