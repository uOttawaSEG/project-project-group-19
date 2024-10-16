package com.example.eams.users;

/**
 * Organizer extends RegisterUser (and User). They can login in MainActivity or
 *      register in OrganizerRegisterActivity.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Organizer extends RegisterUser {

    // INSTANCE VARIABLES
    private String organizationName;

    // CONSTRUCTOR(S)

    /**
     * Parameterized constructor that creates a new Organizer user with given register information.
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
     * @param organizationName
     */
    public Organizer(String email, String password, String firstName, String lastName, String phoneNumber, String streetAddress, String city, String province, String postalCode, String organizationName) {
        super(email, password, firstName, lastName, phoneNumber, streetAddress, city, province, postalCode);
        this.organizationName = organizationName;
    }

    // GETTERS & SETTERS
    /**
     * Gets the organization name of the user.
     * @return the organization name of the user.
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organization name of the Organizer.
     * @param organizationName the organization name to set.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    // PUBLIC METHODS
    /**
     * Attempts to register the user with the given instance variables
     *
     * @return true if successful, false otherwise
     */
    @Override
    public boolean register() {
        return false;
    }
}

