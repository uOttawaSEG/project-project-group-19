package com.example.eams.event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eams.R;
import com.example.eams.users.RegisterUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDialogueFragment extends DialogFragment {

    private String eventTitle;
    private String attendeeEmail;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvPhoneNumber;
    private TextView tvEmail;
    private TextView tvStreet;
    private TextView tvCity;
    private TextView tvProvince;
    private TextView tvPostalCode;
    private Button btnBack;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("eventRegistrations");

    public EventDialogueFragment(String eventTitle, String attendeeEmail){
        this.eventTitle= eventTitle;
        this.attendeeEmail= attendeeEmail;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Attendee Details");

        // Set up layout for Attendee information
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View eventDialogView = inflater.inflate(R.layout.dialog_event_registration_attendee_info, null);
        builder.setView(eventDialogView);

        // Initialize TextViews
        tvFirstName = eventDialogView.findViewById(R.id.event_attendee_first_name);
        tvLastName = eventDialogView.findViewById(R.id.event_attendee_last_name);
        tvPhoneNumber = eventDialogView.findViewById(R.id.event_attendee_phone_number);
        tvEmail = eventDialogView.findViewById(R.id.event_attendee_email);
        tvStreet = eventDialogView.findViewById(R.id.event_attendee_street);
        tvCity = eventDialogView.findViewById(R.id.event_attendee_city);
        tvProvince = eventDialogView.findViewById(R.id.event_attendee_province);
        tvPostalCode = eventDialogView.findViewById(R.id.event_attendee_postal_code);

        retrieveDetails();

        // Exit dialog box
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void retrieveDetails(){
        databaseReference.child(("attendees"))
                .orderByChild("email")
                .equalTo(attendeeEmail)
                .addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot databaseAttendee){
                if(databaseAttendee.exists()){
                    DataSnapshot attendeeInfo = databaseAttendee.getChildren().iterator().next();
                    RegisterUser user = attendeeInfo.getValue(RegisterUser.class);

                    if(user != null){
                        bind(user);
                    }

//                    tvFirstName.setText(attendeeInfo.child("firstName").getValue(String.class));
//                    tvLastName.setText(attendeeInfo.child("lastName").getValue(String.class));
//                    tvEmail.setText(attendeeInfo.child("email").getValue(String.class));
//                    tvPhoneNumber.setText(attendeeInfo.child("phoneNumber").getValue(String.class));
//                    tvStreet.setText(attendeeInfo.child("street").getValue(String.class));
//                    tvCity.setText(attendeeInfo.child("city").getValue(String.class));
//                    tvProvince.setText(attendeeInfo.child("province").getValue(String.class));
//                    tvPostalCode.setText(attendeeInfo.child("postalCode").getValue(String.class));
                }
                else{
                    Toast.makeText(getContext(), "Attendee not registered to event.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){
                Toast.makeText(getContext(), "Attendee not registered to event.", Toast.LENGTH_SHORT).show();
            }

                });
    }
    /**
     * Populates the view with the given user's information
     * @param user the user who's data will be shown
     */
    public void bind(@NonNull RegisterUser user) {
        tvFirstName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());
        tvPhoneNumber.setText(user.getPhoneNumber());
        tvEmail.setText(user.getEmail());
        tvStreet.setText(user.getStreet());
        tvCity.setText(user.getCity());
        tvProvince.setText(user.getProvince());
        tvPostalCode.setText(user.getPostalCode());
    }

}
