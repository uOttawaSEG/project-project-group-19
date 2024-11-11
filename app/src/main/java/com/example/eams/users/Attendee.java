package com.example.eams.users;

import android.widget.Toast;

import com.example.eams.event.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Attendee extends RegisterUser (and User). They can login in MainActivity or
 * register in AttendeeRegisterActivity.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class Attendee extends RegisterUser {

    // List of Event keys that Attendee has signed up for (pending approval)
    ArrayList<String> attendeePendingEventRegistrations;

    // List of Event keys that Attendee has signed up for (and Organizer has approved)
    ArrayList<String> attendeeApprovedEventRegistrations;

    // DO NOT REMOVE: Required for Firebase!!
    public Attendee() {

    }

    /**
     * Creates a new Attendee user with given registration information. <br>
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
     */
    public Attendee(
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String street,
            String city,
            String province,
            String postalCode
    ) {
        super(email, password, firstName, lastName, phoneNumber, street, city, province, postalCode);
        attendeePendingEventRegistrations = new ArrayList<>();
        attendeeApprovedEventRegistrations = new ArrayList<>();
    }

    /**
     * Getter method for the list of pending event registrations
     * @return ArrayList of pending event registrations
     */
    public ArrayList<String> getAttendeePendingEventRegistrations() {
        return attendeePendingEventRegistrations;
    }

    /**
     * Setter method for the list of pending event registrations
     * @param attendeePendingEventRegistrations ArrayList of pending event registrations for Attendee
     */
    public void setAttendeePendingEventRegistrations(ArrayList<String> attendeePendingEventRegistrations) {
        this.attendeePendingEventRegistrations = attendeePendingEventRegistrations;
    }

    /**
     * Getter method for the list of approved event registrations
     * @return
     */
    public ArrayList<String> getAttendeeApprovedEventRegistrations() {
        return attendeeApprovedEventRegistrations;
    }
    /**
     * Setter method for the list of approved event registrations
     * @return
     */
    public void setAttendeeApprovedEventRegistrations(ArrayList<String> attendeeApprovedEventRegistrations) {
        this.attendeeApprovedEventRegistrations = attendeeApprovedEventRegistrations;
    }

    public void addEventRegistrationToPending (String eventKey){

        // Only add if attendee has not already signed up for event
        if(! (attendeePendingEventRegistrations.contains(eventKey)) && ! (attendeeApprovedEventRegistrations.contains(eventKey)) ){

            DatabaseReference eventInfo = FirebaseDatabase.getInstance()
                    .getReference("events")
                    .child(eventKey)
                    .child("isApprovedAutomatically");

            eventInfo.get().addOnCompleteListener(task -> {
                if(task.isSuccessful() && task.getResult().exists()){
                    Boolean isApprovedAutomatically = task.getResult().getValue(Boolean.class);

                    // If Event isApprovedAutomatically, add Event directly to attendeeApprovedEventRegistrations
                    if(isApprovedAutomatically != null && isApprovedAutomatically){
                        attendeeApprovedEventRegistrations.add(eventKey);
                        updateFirebaseAttendeeEventRegistrations();
                    }

                    // Otherwise, add Event to attendeePendingEventRegistrations to be approved/rejected by Organizer
                    else{
                        attendeePendingEventRegistrations.add(eventKey);
                        updateFirebaseAttendeeEventRegistrations();
                    }
                }
            });
        }

    }

    // Move an Event from attendeePendingEventRegistrations to attendeeApprovedEventRegistrations
    public void approveEventRegistration(String eventKey) {

        if (attendeePendingEventRegistrations.contains(eventKey)) {

            String approvedEventKey = eventKey;
            attendeePendingEventRegistrations.remove(eventKey);
            attendeeApprovedEventRegistrations.add(approvedEventKey);

            updateFirebaseAttendeeEventRegistrations();
        }
    }

    public void rejectEventRegistration(String eventKey){
        if (attendeePendingEventRegistrations.contains(eventKey)) {

            attendeePendingEventRegistrations.remove(eventKey);
            updateFirebaseAttendeeEventRegistrations();
        }
    }

    // Update event ArrayLists in Firebase
    private void updateFirebaseAttendeeEventRegistrations(){
        DatabaseReference attendeesReference = FirebaseDatabase.getInstance().getReference("attendees");

        // Find this Attendee object in Firebase
        attendeesReference.orderByChild("email").equalTo(this.getEmail()).get().addOnCompleteListener(task -> {

            if(task.isSuccessful() && task.getResult().exists()){
                DataSnapshot attendeeSnapshot = task.getResult().getChildren().iterator().next();
                String attendeeKey = attendeeSnapshot.getKey();

                attendeesReference.child(attendeeKey).child("attendeePendingEventRegistrations").setValue(attendeePendingEventRegistrations);
                attendeesReference.child(attendeeKey).child("attendeeApprovedEventRegistrations").setValue(attendeeApprovedEventRegistrations);
            }
        });
    }
}
