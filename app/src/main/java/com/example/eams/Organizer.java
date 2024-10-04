package com.example.eams;

public class Organizer extends RegisterUser {
    private String organizationName;

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
