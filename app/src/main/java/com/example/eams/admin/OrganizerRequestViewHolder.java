package com.example.eams.admin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eams.R;
import com.example.eams.users.Organizer;

public class OrganizerRequestViewHolder extends RequestViewHolder {
    private TextView tvOrganization;

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

    public void bind(Organizer organizer) {
        super.bind(organizer);
        setOrganization(organizer.getOrganization());
    }

    public void setTvOrganization(int id) {
        this.tvOrganization = itemView.findViewById(id);
    }

    public void setOrganization(String organization) {
        if (tvOrganization == null) {
            return;
        }
        tvOrganization.setText(organization);
    }
}
