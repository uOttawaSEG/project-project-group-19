package com.example.eams.admin.request;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eams.R;
import com.example.eams.users.Organizer;
import com.example.eams.users.RegisterUser;

/**
 * A ViewHolder for an individual Organizer request
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerRequestViewHolder extends RequestViewHolder {
    private TextView tvOrganization;

    /**
     * Constructor for OrganizerRequestViewholder
     * @param itemView View to initialize the ViewHolder
     */
    public OrganizerRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        setTvFirstName(R.id.organizer_rejected_request_first_name);
        setTvLastName(R.id.organizer_rejected_request_last_name);
        setTvPhoneNumber(R.id.organizer_rejected_request_phone_number);
        setTvEmail(R.id.organizer_rejected_request_email);
        setTvStreet(R.id.organizer_rejected_request_street);
        setTvCity(R.id.organizer_rejected_request_city);
        setTvProvince(R.id.organizer_rejected_request_province);
        setTvPostalCode(R.id.organizer_rejected_request_postal_code);
        setTvOrganization(R.id.organizer_rejected_request_organization);
        setBtnAccept(R.id.organizer_rejected_request_accept);
    }

    @Override
    public void bind(@NonNull RegisterUser user) {
        super.bind(user);
        setOrganization(((Organizer) user).getOrganization());
    }

    /**
     * Finds the Organization TextView
     * @param id the id of the element
     */
    public void setTvOrganization(int id) {
        this.tvOrganization = itemView.findViewById(id);
    }

    /**
     * Sets the content of the Organization TextView
     * @param organization the content to be set
     */
    public void setOrganization(String organization) {
        if (tvOrganization == null) {
            return;
        }
        tvOrganization.setText(organization);
    }
}
