package com.example.eams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class to enforce the register method and to store common instance variables
 */
public abstract class RegisterUser {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    /**
     * Validates the first name
     * @return true if firstName is valid, false otherwise
     */
    public boolean validateFirstName() {
        return isAlphabetic(firstName);
    }

    /**
     * Validates the last name
     * @return true if lastName is valid, false otherwise
     */
    public boolean validateLastName() {
        return isAlphabetic(lastName);
    }

    /**
     * Validates the phone number
     * @return true if phoneNumber is valid, false otherwise
     */
    public boolean validatePhoneNumber() {
        Pattern pattern = Pattern.compile("^[0-9]\\{3}-?[0-9]\\{3}-?[0-9]\\{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    /**
     * Validates the address
     * @return true if address is valid, false otherwise
     */
    public boolean validateAddress() {
        return true;
    }

    /**
     * Attempts to register the user with the given instance variables
     * @return true if successful, false otherwise
     */
    public abstract boolean register();

    /**
     * Gets the first name of the user.
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number of the user.
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address of the user.
     * @return the address of the user.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Tests if a string is strictly alphabetic
     * @param s the string to be tested
     * @return true if s is alphabetic, false otherwise
     */
    private boolean isAlphabetic(String s) {
        Pattern pattern = Pattern.compile("^[A-Za-z]$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
