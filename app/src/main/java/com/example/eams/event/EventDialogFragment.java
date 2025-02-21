package com.example.eams.event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eams.R;
import com.example.eams.organizer.OrganizerViewEventRegistrationsActivity;
import com.example.eams.users.Attendee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventDialogFragment extends DialogFragment {

    private String requestType;
    // The attendee who has the info to be shown
    private Attendee attendee;
    private String eventKey;

    public EventDialogFragment(String requestType, Attendee attendee, String eventKey) {
        this.requestType = requestType;
        this.attendee = attendee;
        this.eventKey = eventKey;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attendee Details");

        // Set up layout for Attendee information
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_event_registration_attendee_info, null);

        // Initialize TextViews
        TextView tvFirstName = eventDialogView.findViewById(R.id.event_attendee_first_name);
        TextView tvLastName = eventDialogView.findViewById(R.id.event_attendee_last_name);
        TextView tvPhoneNumber = eventDialogView.findViewById(R.id.event_attendee_phone_number);
        TextView tvEmail = eventDialogView.findViewById(R.id.event_attendee_email);
        TextView tvStreet = eventDialogView.findViewById(R.id.event_attendee_street);
        TextView tvCity = eventDialogView.findViewById(R.id.event_attendee_city);
        TextView tvProvince = eventDialogView.findViewById(R.id.event_attendee_province);
        TextView tvPostalCode = eventDialogView.findViewById(R.id.event_attendee_postal_code);

        // Set all attendee data
        tvFirstName.setText(attendee.getFirstName());
        tvLastName.setText(attendee.getLastName());
        tvPhoneNumber.setText(attendee.getPhoneNumber());
        tvEmail.setText(attendee.getEmail());
        tvStreet.setText(attendee.getStreet());
        tvCity.setText(attendee.getCity());
        tvProvince.setText(attendee.getProvince());
        tvPostalCode.setText(attendee.getPostalCode());

        builder.setView(eventDialogView);

        // Database variables
        String attendeeKey = attendee.getDatabaseKey();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventReference = databaseReference.child("events/" + eventKey + "/registeredAttendees/" + attendeeKey);
        DatabaseReference attendeeReference = databaseReference.child("users/attendees/approved/" + attendeeKey + "/eventsRegisteredTo/" + eventKey);

        // Setup buttons for pending
        if (requestType.equals("pending")) {
            // Accept the request
            builder.setPositiveButton("Accept", (dialog, id) -> {
                attendeeReference.setValue("approved");
                eventReference.setValue("approved");

                Toast.makeText(getActivity(), attendee.getFirstName() + " " + attendee.getLastName() + " has been approved.", Toast.LENGTH_SHORT).show();
            });

            // Reject the request
            builder.setNegativeButton("Reject", (dialog, id) -> {
                eventReference.removeValue();
                attendeeReference.removeValue();

                Toast.makeText(getActivity(), attendee.getFirstName() + " " + attendee.getLastName() + " has been rejected.", Toast.LENGTH_SHORT).show();
            });
        }

        // Create and return
        return builder.create();
    }

}

