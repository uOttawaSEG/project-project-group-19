package com.example.eams.users;

/**
 * A class representing a user who is an attendee
 */
public class Attendee extends RegisterUser {
    @Override
    public boolean register() {
        return false;
    }

    public  Attendee(String email, String password, String firstName, String lastName, String phoneNumber, String streetAddress, String city, String province, String postalCode) {
        super(email, password, firstName, lastName, phoneNumber, streetAddress, city, province, postalCode);
    }
}


