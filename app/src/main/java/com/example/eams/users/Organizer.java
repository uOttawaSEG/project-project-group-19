package com.example.eams.users;

public class Organizer extends RegisterUser {
    private String organization;

    // DO NOT REMOVE: Required for Firebase!!
    public Organizer() {

    }
    
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

    /**
     * Gets the organization name of the user.
     * @return the organization name of the user.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the first name of the user.
     * @param organization the first name to set.
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
