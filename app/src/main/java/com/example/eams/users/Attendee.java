package com.example.eams.users;

/**
 * Attendee extends RegisterUser (and User). They can login in MainActivity or
 * register in AttendeeRegisterActivity.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Attendee extends RegisterUser {

    // DO NOT REMOVE: Required for Firebase!!
    public Attendee() {

    }

    /**
     * Creates a new Attendee user with given registration information. <br>
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param street
     * @param city
     * @param province
     * @param postalCode
     */
    public Attendee(
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String street,
            String city,
            String province,
            String postalCode
    ) {
        super(email, password, firstName, lastName, phoneNumber, street, city, province, postalCode);
    }

    @Override
    public boolean register() {
        return false;
    }
}
