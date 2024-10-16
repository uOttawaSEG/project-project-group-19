package com.example.eams.users;

/**
 * Organizer extends RegisterUser (and User). They can login in MainActivity or
 * register in OrganizerRegisterActivity.
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Organizer extends RegisterUser {
    // Instance variables
    private String organization;


    // Constructors
    // DO NOT REMOVE: Required for Firebase!!
    public Organizer() {

    }

    /**
     * Parameterized constructor that creates a new Organizer user with given register information.
     * Calls constructor of superclass RegisterUser.
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
     * @param organization
     */
    public Organizer(
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String street,
            String city,
            String province,
            String postalCode,
            String organization
    ) {
        super(email, password, firstName, lastName, phoneNumber, street, city, province, postalCode);
        this.organization = organization;
    }


    // Getters + Setters

    /**
     * @return user's organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set.
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }


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
