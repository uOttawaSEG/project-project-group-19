package com.example.eams.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract class to enforce the register method and to store common instance variables
 * RegisterUser extends User. It is extended by Organizer and Attendee.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public abstract class RegisterUser extends User {

    // INSTANCE VARIABLES
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;

    /**
     * Parameterized constructor that creates a new RegisterUser with given register information.
     * Calls constructor of superclass User to set email and password.
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
    public RegisterUser(String email, String password, String firstName, String lastName, String phoneNumber, String streetAddress, String city, String province, String postalCode) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    // PUBLIC METHODS

    /**
     * Attempts to register the user with the given instance variables
     * @return true if successful, false otherwise
     */
    public abstract boolean register();

    // First Name Methods

    /**
     * Validates the first name
     * @return true if firstName is valid, false otherwise
     */
    public boolean validateFirstName() {
        return isAlphabetic(firstName);
    }

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

    // Last Name Methods
    /**
     * Validates the last name
     * @return true if lastName is valid, false otherwise
     */
    public boolean validateLastName() {
        return isAlphabetic(lastName);
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

    // Phone Number Methods

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

    // Address methods

    /**
     * Validates the address
     * @return true if address is valid, false otherwise
     */
    public boolean validateAddress() {
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

        // make sure streetAddress is valid
        Pattern streetAddressPattern = Pattern.compile("^\\d+\\s[a-zA-Z.]+$");
        Matcher streetAddressMatcher = streetAddressPattern.matcher(postalCode);
        if (!streetAddressMatcher.find()) {
            return false;
        }
        return true; // this statement is reached if every test is passed
    }

    /**
     * Gets the street address of the user.
     * @return the street address of the user.
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Gets the city of the user.
     * @return the city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the province of the user.
     * @return the province of the user.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Gets the postal code of the user.
     * @return the postal code of the user.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the address of the user.
     * @param streetAddress the address to set.
     * @param city the address to set.
     * @param province the address to set.
     * @param postalCode the address to set.
     */
    public void setAddress(String streetAddress, String city, String province, String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
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
