package com.example.eams.users;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class to enforce the register method and to store common instance variables
 * RegisterUser extends User. It is extended by Organizer and Attendee.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public abstract class RegisterUser extends User {

    // Instance variables
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String street;
    private String city;
    private String province;
    private String postalCode;

    // Constructors
    // DO NOT REMOVE: Required for Firebase!!
    public RegisterUser() {

    }

    /**
     * Creates a new RegisterUser with given information.
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
    public RegisterUser(
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

        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }


    // Getters + Setters

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the street address of the user.
     *
     * @return the street address of the user.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets the city of the user.
     *
     * @return the city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the province of the user.
     *
     * @return the province of the user.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Gets the postal code of the user.
     *
     * @return the postal code of the user.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the address of the user.
     *
     * @param street     the address to set.
     * @param city       the address to set.
     * @param province   the address to set.
     * @param postalCode the address to set.
     */
    public void setAddress(String street, String city, String province, String postalCode) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    /**
     * Validates the first name
     *
     * @return true if firstName is valid, false otherwise
     */
    public boolean firstNameIsValid() {
        return isAlphabetic(firstName);
    }

    /**
     * Validates the last name
     *
     * @return true if lastName is valid, false otherwise
     */
    public boolean lastNameIsValid() {
        return isAlphabetic(lastName);
    }

    /**
     * Validates the phone number
     *
     * @return true if phoneNumber is valid, false otherwise
     */
    public boolean phoneNumberIsValid() {
        Pattern pattern = Pattern.compile("^[0-9]{3}-?[0-9]{3}-?[0-9]{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    /**
     * Validates the address
     *
     * @return true if address is valid, false otherwise
     */
    public boolean addressIsValid() {
        // make sure city and province is alphabetic
        if (!isAlphabetic(city) || !isAlphabetic(province)) {
            return false;
        }

        // make sure postal code is valid
        postalCode = postalCode.replace(" ", ""); // gets rid of any spaces in postalCode
        Pattern postalCodePattern = Pattern.compile("^[A-Za-z]\\d[A-Za-z]\\d[A-Za-z]\\d$");
        Matcher postalCodeMatcher = postalCodePattern.matcher(postalCode);
        if (!postalCodeMatcher.find()) {
            return false;
        }

        // make sure street is valid
        Pattern streetPattern = Pattern.compile("^\\d+\\s[a-zA-Z.]+\\s[a-zA-Z.]+\\.?$");
        Matcher streetMatcher = streetPattern.matcher(street);
        if (!streetMatcher.find()) {
            return false;
        }
        return true; // this statement is reached if every test is passed
    }


    /**
     * Tests if a string is strictly alphabetic and not empty
     *
     * @param s the string to be tested
     * @return true if s is alphabetic and not empty, false otherwise
     */
    private boolean isAlphabetic(String s) {
        if (s.isEmpty()) return false;

        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

}