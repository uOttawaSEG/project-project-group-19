package com.example.eams.event;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {

    // instance variables
    private String title;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private boolean approvalIsAutomatic;
//    private List<String> pendingAttendeeKeys;
//    private List<String> approvedAttendeeKeys;
    //private String organizerID;

    // required for firebase (?)
    public Event() {

    }

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
     * @param postalCode  //@param organizerID
     */
    public Event(
            String title,
            String description,
            String date,
            String startTime,
            String endTime,
            String street,
            String city,
            String province,
            String postalCode,
            boolean approvalIsAutomatic
            //String organizerID
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
//        this.pendingAttendeeKeys = new ArrayList<>();
//        this.approvedAttendeeKeys = new ArrayList<>();
        //this.organizerID = organizerID;
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
    public String getDate() {
        return date;
    }

    /**
     * @return event start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return event end time
     */
    public String getEndTime() {
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
     *
     * @param value is true if automatic, and false if manual
     */
    public void setAutomaticApproval(boolean value) {
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
     * @return FireBase push key for pending attendees of an event
     */
//    public List<String> getPendingAttendeeKeys() {
//        return pendingAttendeeKeys;
//    }

    /**
     * @return FireBase push key for approved attendees of an event
     */
//    public List<String> getApprovedAttendeeKeys() {
//        return pendingAttendeeKeys;
//    }

    /**
     * Gets the organizerID of the event.
     *
     * @return the organizerID of the event.
     */
    //public String getOrganizerID() {
    //  return organizerID;
    //}

    //TODO: validate fields - could potentially create address class to avoid reusing code already in RegisterUser class
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean titleIsValid() {
        return !title.isEmpty();
    }

    public boolean descriptionIsValid() {
        return !title.isEmpty();
    }

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

    private boolean isAlphabetic(String s) {
        if (s.isEmpty()) return false;

        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
