package com.example.eams.admin;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.eams.R;
import com.example.eams.users.Attendee;

public class AttendeeRequestViewHolder extends RequestViewHolder {
    public AttendeeRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        setTvFirstName(R.id.attendee_rejected_request_first_name);
        setTvLastName(R.id.attendee_rejected_request_last_name);
        setTvPhoneNumber(R.id.attendee_rejected_request_phone_number);
        setTvEmail(R.id.attendee_rejected_request_email);
        setTvStreet(R.id.attendee_rejected_request_street);
        setTvCity(R.id.attendee_rejected_request_city);
        setTvProvince(R.id.attendee_rejected_request_province);
        setTvPostalCode(R.id.attendee_rejected_request_postal_code);
        setBtnAccept(R.id.attendee_rejected_request_accept);
    }
}
