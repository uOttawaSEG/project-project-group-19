package com.example.eams.event;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.users.RegisterUser;

/**
 * ViewHolder for a registration request
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class EventRegistrationViewHolder extends RecyclerView.ViewHolder  {

    private TextView tvEmail;
    private Button btnViewAttendeeDetails;

    /**
     * Constructor for a EventRegistrationViewHolder
     * @param itemView the View used to initialize the EventRegistrationViewHolder
     */
    public EventRegistrationViewHolder(@NonNull View itemView) {

        super(itemView);
        setTvEmail(R.id.event_registration_attendee_email);
        setBtnViewAttendeeDetails(R.id.btn_event_registration_view_attendee_details);
    }

    /**
     * Populates the view with the given user's information
     * @param eventRegistration
     */
    public void bind(@NonNull EventRegistration eventRegistration) {
        setTvEmail(eventRegistration.getRegisteredAttendeeEmail());
    }

    /**
     * Sets the email of the registered Attendee
     * @param id id of the TextView
     */
    public void setTvEmail(int id) {
        this.tvEmail = itemView.findViewById(id);
    }

    /**
     * Sets the email of the registered Attendee
     * @param email the email of the registered Attendee
     */
    public void setTvEmail(String email) {
        tvEmail.setText(email);
    }

    /**
     * Finds the accept button
     * @param id the id of the element
     */
    public void setBtnViewAttendeeDetails(int id) {
        this.btnViewAttendeeDetails = itemView.findViewById(id);
    }

    public void setViewRegistrationsOnClickListener(View.OnClickListener l) {

        if (btnViewAttendeeDetails == null) {
            return;
        }

        btnViewAttendeeDetails.setOnClickListener(v -> {
            EventDialogFragment eventDialog = new EventDialogFragment(tvEmail.getText().toString());
        });
    }


}
