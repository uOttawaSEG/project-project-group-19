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
     * @param streetAddress must contain street number and street name.
     * @param city must be alphabetic.
     * @param postalCode must be 6 characters and have correct letter/number sequence (ie. A1B 2C3 or A1B2C3).
     * @param province must be alphabetic.
     * @param country must be alphabetic.
     */
    public boolean validateAddress(String streetAddress, String city, String postalCode, String province, String country) {
        // make sure city, province, and country is alphabetic
        if (!isAlphabetic(city) || !isAlphabetic(province) || !isAlphabetic(country)) {
            return false;
        }

        // make sure postal code is valid
        postalCode = postalCode.replace(" ", ""); // gets rid of any spaces in postalCode
        if (postalCode.length() != 6)
            return false;
        char[] postalCodeCharArray = postalCode.toCharArray();
        for (int i = 0; i < 6; i++) {
            String c = String.valueOf(postalCodeCharArray[i]);
            if (i % 2 == 0 && !isAlphabetic(c)) { // if it should be a letter and it is not
                return false;
            } else if (Integer.getInteger(c) == null) { // if it should be a number and it is not
                    return false;
            }
            // else it is valid
        }

        // make sure streetAddress is valid
        char[] streetAddressArray = streetAddress.replace(" ", "").toCharArray();
        int j = 0; // this will track where the first letter is in streetAddress
        for (int i = 0; i < streetAddressArray.length; i++) {
            String c = String.valueOf(postalCodeCharArray[i]);
            if (isAlphabetic(c)) {
                j = i;
            }
        }
        if (j == 0) {
            return false; // this would mean streetAddress contains no numbers
        } else { // streetAddress contains numbers
            String streetName = streetAddress.substring(j); // gets streetName from streetAddress
            if (!isAlphabetic(streetName)) { // if the streetName is not alphabetic, return false
                return false;
            }
        }
        return true; // this statement is reached if every test is passed
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
        if (s.isEmpty())
            return false;

        Pattern pattern = Pattern.compile("^[A-Za-z]$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
