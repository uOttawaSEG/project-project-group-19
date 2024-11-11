package com.example.eams.event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eams.R;
import com.example.eams.users.Attendee;

public class EventDialogFragment extends DialogFragment {

    /**
     * The attendee who has the info to be shown
     */
    private Attendee attendee;

    public EventDialogFragment(Attendee attendee) {
        this.attendee = attendee;
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


        builder.setView(eventDialogView)
                // Accept the request
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                // Reject the request
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // Create and return
        return builder.create();
    }

}
