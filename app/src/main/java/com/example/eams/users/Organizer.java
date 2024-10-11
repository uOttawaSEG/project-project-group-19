package com.example.eams.users;

public class Organizer extends RegisterUser {
    private String organizationName;

    public Organizer(String email, String password, String firstName, String lastName, String phoneNumber, String streetAddress, String city, String province, String postalCode, String organizationName) {
        super(email, password, firstName, lastName, phoneNumber, streetAddress, city, province, postalCode);
        this.organizationName = organizationName;
    }

    /**
     * Gets the organization name of the user.
     * @return the organization name of the user.
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the first name of the user.
     * @param organizationName the first name to set.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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

