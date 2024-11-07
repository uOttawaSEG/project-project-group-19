package com.example.eams.users;

import java.util.Date;

public class Event {

    // instance variables
    private String title;
    private String description;
    private Date date;
    private Date startTime;
    private Date endTime;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private boolean approvalIsAutomatic;
    private String organizerID;

    // required for firebase (?)
    public Event() {    }


    /**
     * Parameterized constructor that creates a new Event with given information.
     *
     * @param title
     * @param description
     * @param date
     * @param startTime
     * @param endTime
     * @param street
     * @param city
     * @param province
     * @param postalCode
     * @param organizerID
     */
    public Event(
            String title,
            String description,
            Date date,
            Date startTime,
            Date endTime,
            String street,
            String city,
            String province,
            String postalCode,
            boolean approvalIsAutomatic,
            String organizerID
    ) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.approvalIsAutomatic = approvalIsAutomatic;
        this.organizerID = organizerID;
    }

    // Getters

    /**
     * @return event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return event description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @return event date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return event start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return event end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Gets the street address of the event.
     *
     * @return the street address of the event.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets the city of the event.
     *
     * @return the city of the event.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the province of the event.
     *
     * @return the province of the event.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Gets the postal code of the event.
     *
     * @return the postal code of the event.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets attendee registration approval to manual or automatic.
     * @param value is true if automatic, and false if manual
     */
    public void setAutomaticApproval(boolean value){
        approvalIsAutomatic = value;
    }

    /**
     * Determines if attendee registration is approved automatically or manually.
     *
     * @return true if attendee registration is set to approved automatically.
     */
    public boolean approvalIsAutomatic() {
        return approvalIsAutomatic;
    }

    /**
     * Gets the organizerID of the event.
     *
     * @return the organizerID of the event.
     */
    public String getOrganizerID() {
        return organizerID;
    }

    //TODO: validate fields - could potentially create address class to avoid reusing code already in RegisterUser class

}
