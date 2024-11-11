package com.example.eams.event;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.users.Attendee;

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
public class EventRegistrationAttendeeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvEmail;
    private Button btnViewDetails;

    /**
     * Creates a new EventRegistrationViewHolder
     *
     * @param itemView the View used to initialize the EventRegistrationViewHolder
     */
    public EventRegistrationAttendeeViewHolder(@NonNull View itemView) {
        super(itemView);
        setTvEmail(R.id.event_registration_attendee_email);
        setBtnViewDetails(R.id.btn_event_registration_view_attendee_details);
    }

    /**
     * Populates the view with the given user's information
     *
     * @param attendee
     */
    public void bind(@NonNull Attendee attendee) {
        setTvEmail(attendee.getEmail());
    }

    /**
     * Sets the email of the registered Attendee
     *
     * @param id id of the TextView
     */
    public void setTvEmail(int id) {
        this.tvEmail = itemView.findViewById(id);
    }

    /**
     * Sets the email of the registered Attendee
     *
     * @param email the email of the registered Attendee
     */
    public void setTvEmail(String email) {
        tvEmail.setText(email);
    }

    /**
     * Finds the accept button
     *
     * @param id the id of the element
     */
    public void setBtnViewDetails(int id) {
        this.btnViewDetails = itemView.findViewById(id);
    }

    public void setBtnViewAttendeeDetailsOnClickListener(View.OnClickListener listener) {

        if (btnViewDetails == null) {
            return;
        }

        btnViewDetails.setOnClickListener(listener);
    }


}
