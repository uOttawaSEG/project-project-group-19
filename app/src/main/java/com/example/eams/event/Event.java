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

    private String databaseKey;
    private String organizerKey;
    private boolean approvalIsAutomatic;

    // required for firebase (!)
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
            String organizerKey,
            boolean approvalIsAutomatic
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
        this.organizerKey = organizerKey;
        this.approvalIsAutomatic = approvalIsAutomatic;
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
     * Determines if attendee registration is approved automatically or manually.
     *
     * @return true if attendee registration is set to approved automatically.
     */
    public boolean approvalIsAutomatic() {
        return approvalIsAutomatic;
    }

    /**
     * Gets the event's database key.
     * @return the database key of the event
     */
    public String getDatabaseKey(){
        return databaseKey;
    }

    /**
     * Gets the organizer of the event's database key.
     *
     * @return the database key of the organizer.
     */
    public String getOrganizerKey() {
      return organizerKey;
    }

    // Setters

    /**
     * Sets the title of the event.
     * @param title the title to set the event to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the event
     * @param description the new event description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the date of the event.
     * @param date the date of the event in string format.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the start time of the event.
     * @param startTime the start time of the event. Must be in 30 minute increments.
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the event.
     * @param endTime the end time of the event. Must be in 30 minute increments.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the street of the event.
     * @param street the street of the event.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Sets the city of the event.
     * @param city the city of the event.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the province of the event.
     * @param province the province of the event.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Sets the postal code of the event.
     * @param postalCode the postal code of the event.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the event's database key.
     * @param databaseKey the database key of the event.
     */
    public void setDatabaseKey(String databaseKey){
        this.databaseKey = databaseKey;
    }

    /**
     * Sets the event organizer's key.
     * @param organizerKey the key of the event's organizer.
     */
    public void setOrganizerKey(String organizerKey){
        this.organizerKey = organizerKey;
    }

    /**
     * Sets attendee registration approval to manual or automatic.
     *
     * @param value is true if automatic, and false if manual
     */
    public void setAutomaticApproval(boolean value) {
        approvalIsAutomatic = value;
    }

    // Validation Methods

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
