package com.example.eams.event;

/**
 *  Class representing an Attendee's registration to an Event
 *  Used to facilitate searching for all Events an Attendee signed up for
 *
 *  @author Alex Ajersch
 *  @author Brooklyn McClelland
 *  @author Moïse Kenge Ngoyi
 *  @author Naomi Braun
 *  @author Rachel Qi
 *  @author Steven Wu
 */
public class EventRegistration {

    String eventTitle;
    String attendeeEmail;

    // Constructor for Firebase
    public EventRegistration(){

    }

    /**
     * Create an EventRegistration object with Event and registered Attendee
     * @param eventTitle Title of the event
     * @param attendeeEmail Email of the Attendee registering
     */
    public EventRegistration(String eventTitle, String attendeeEmail){

        this.eventTitle = eventTitle;
        this.attendeeEmail = attendeeEmail;

    }

    /**
     * Getter method for the Event title
     * @return eventTitle
     */
    public String getEventTitle(){return eventTitle;}

    /**
     * Getter method for the Attendee's email
     * @return attendeeEmail
     */
    public String getRegisteredAttendeeEmail(){return attendeeEmail;}
}
