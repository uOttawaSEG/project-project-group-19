package com.example.eams.admin.request;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.eams.R;

/**
 * A ViewHolder for an individual Attendee request that can be rejected
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AttendeeRejectableRequestViewHolder extends AttendeeRequestViewHolder implements RejectableRequest {
    private RejectableRequest rejecter;

    /**
     * Constructor for AttendeeRejectableRequestViewholder
     * @param itemView View to initialize the ViewHolder
     */
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
