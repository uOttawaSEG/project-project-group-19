package com.example.eams.users;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
//    List<String> pendingEventRegistrationKeys;

    // List of Event keys that Attendee has signed up for (and Organizer has approved)
//    List<String> approvedEventRegistrationKeys;

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
//        pendingEventRegistrationKeys = new ArrayList<>();
//        approvedEventRegistrationKeys = new ArrayList<>();
    }

//    /**
//     * @return list of pending event registrations
//     */
//    public List<String> getPendingEventRegistrationKeys() {
//        return pendingEventRegistrationKeys;
//    }
//
//    /**
//     * @return list of approved event registrations
//     */
//    public List<String> getApprovedEventRegistrationKeys() {
//        return approvedEventRegistrationKeys;
//    }
//
//    public void addEventRegistrationToPending(String eventKey) {
//
//        // Only add if attendee has not already signed up for event
//        if (!(pendingEventRegistrationKeys.contains(eventKey)) && !(approvedEventRegistrationKeys.contains(eventKey))) {
//
//            DatabaseReference eventInfo = FirebaseDatabase.getInstance()
//                    .getReference("events")
//                    .child(eventKey)
//                    .child("isApprovedAutomatically");
//
//            eventInfo.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful() && task.getResult().exists()) {
//                    Boolean isApprovedAutomatically = task.getResult().getValue(Boolean.class);
//
//                    // If Event isApprovedAutomatically, add Event directly to attendeeApprovedEventRegistrations
//                    if (isApprovedAutomatically != null && isApprovedAutomatically) {
//                        approvedEventRegistrationKeys.add(eventKey);
//                        updateFirebaseAttendeeEventRegistrations();
//                    }
//
//                    // Otherwise, add Event to attendeePendingEventRegistrations to be approved/rejected by Organizer
//                    else {
//                        pendingEventRegistrationKeys.add(eventKey);
//                        updateFirebaseAttendeeEventRegistrations();
//                    }
//                }
//            });
//        }
//
//    }

    // Move an Event from attendeePendingEventRegistrations to attendeeApprovedEventRegistrations
//    public void approveEventRegistration(String eventKey) {
//
//        if (pendingEventRegistrationKeys.contains(eventKey)) {
//
//            String approvedEventKey = eventKey;
//            pendingEventRegistrationKeys.remove(eventKey);
//            approvedEventRegistrationKeys.add(approvedEventKey);
//
//            updateFirebaseAttendeeEventRegistrations();
//        }
//    }
//
//    public void rejectEventRegistration(String eventKey) {
//        if (pendingEventRegistrationKeys.contains(eventKey)) {
//
//            pendingEventRegistrationKeys.remove(eventKey);
//            updateFirebaseAttendeeEventRegistrations();
//        }
////    }
//
//    // Update event ArrayLists in Firebase
//    private void updateFirebaseAttendeeEventRegistrations() {
//        DatabaseReference attendeesReference = FirebaseDatabase.getInstance().getReference("attendees");
//
//        // Find this Attendee object in Firebase
//        attendeesReference.orderByChild("email").equalTo(this.getEmail()).get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful() && task.getResult().exists()) {
//                DataSnapshot attendeeSnapshot = task.getResult().getChildren().iterator().next();
//                String attendeeKey = attendeeSnapshot.getKey();
//
//                attendeesReference.child(attendeeKey).child("attendeePendingEventRegistrations").setValue(pendingEventRegistrationKeys);
//                attendeesReference.child(attendeeKey).child("attendeeApprovedEventRegistrations").setValue(approvedEventRegistrationKeys);
//            }
//        });
//    }
}
