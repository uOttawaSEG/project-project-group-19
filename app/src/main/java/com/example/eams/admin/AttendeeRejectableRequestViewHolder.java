package com.example.eams.admin;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.eams.R;

public class AttendeeRejectableRequestViewHolder extends AttendeeRequestViewHolder implements RejectableRequest {
    private RejectableRequest rejecter;

    public AttendeeRejectableRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        rejecter = new RequestRejecter(itemView);
        setTvFirstName(R.id.attendee_request_first_name);
        setTvLastName(R.id.attendee_request_last_name);
        setTvPhoneNumber(R.id.attendee_request_phone_number);
        setTvEmail(R.id.attendee_request_email);
        setTvStreet(R.id.attendee_request_street);
        setTvCity(R.id.attendee_request_city);
        setTvProvince(R.id.attendee_request_province);
        setTvPostalCode(R.id.attendee_request_postal_code);
        setBtnAccept(R.id.attendee_request_accept);
        setBtnReject(R.id.attendee_request_reject);
    }

    @Override
    public void setBtnReject(int id) {
        rejecter.setBtnReject(id);
    }

    @Override
    public void setRejectOnClickListener(View.OnClickListener l) {
        rejecter.setRejectOnClickListener(l);
    }
}
