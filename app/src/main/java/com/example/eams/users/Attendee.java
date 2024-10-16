package com.example.eams.users;

/**
 * A class representing a user who is an attendee
 */
public class Attendee extends RegisterUser {

    // DO NOT REMOVE: Required for Firebase!!
    public Attendee() {

    }

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
