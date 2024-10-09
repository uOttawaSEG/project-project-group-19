package com.example.eams.users;

/**
 * A class representing a user who is an attendee
 */
public class Attendee extends RegisterUser {
    @Override
    public boolean register() {
        return false;
    }
}
