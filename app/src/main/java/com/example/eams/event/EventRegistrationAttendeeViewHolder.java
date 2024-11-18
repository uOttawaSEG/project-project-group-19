package com.example.eams.event;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvEmail;
    private CardView selectedAttendee;

    /**
     * Creates a new EventRegistrationViewHolder
     *
     * @param itemView the View used to initialize the EventRegistrationViewHolder
     */
    public EventRegistrationAttendeeViewHolder(@NonNull View itemView) {
        super(itemView);
        setTvFirstName(R.id.event_reg_attendee_first_name);
        setTvLastName(R.id.event_reg_attendee_last_name);
        setTvEmail(R.id.event_registration_attendee_email);
        setSelectedAttendee(R.id.event_registered_attendee);
    }

    /**
     * Populates the view with the given user's information
     *
     * @param attendee
     */
    public void bind(@NonNull Attendee attendee) {
        setFirstName(attendee.getFirstName());
        setLastName(attendee.getLastName());
        setTvEmail(attendee.getEmail());
    }

    /**
     * Sets the first name of the registered Attendee
     *
     * @param id id of the TextView
     */
    public void setTvFirstName(int id) {
        this.tvFirstName = itemView.findViewById(id);
    }

    /**
     * Sets the content of the First Name TextView
     * @param firstName the content to be set
     */
    private void setFirstName(String firstName) {
        if (tvFirstName == null) {
            return;
        }
        tvFirstName.setText(firstName);
    }

    /**
     * Sets the last name of the registered Attendee
     *
     * @param id id of the TextView
     */
    public void setTvLastName(int id) {
        this.tvLastName = itemView.findViewById(id);
    }


    /**
     * Sets the content of the Last Name TextView
     * @param lastName the content to be set
     */
    private void setLastName(String lastName) {
        if (tvLastName == null) {
            return;
        }
        tvLastName.setText(lastName);
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
     * Sets the content of the Email TextView
     * @param email the content to be set
     */
    public void setTvEmail(String email) {
        if (tvEmail == null) {
            return;
        }
        tvEmail.setText(email);
    }

    /**
     * Finds the accept button
     *
     * @param id the id of the element
     */
    public void setSelectedAttendee(int id) {
        this.selectedAttendee = itemView.findViewById(id);
    }

    public void setBtnViewAttendeeDetailsOnClickListener(View.OnClickListener listener) {

        if (selectedAttendee == null) {
            return;
        }

        selectedAttendee.setOnClickListener(listener);
    }


}
