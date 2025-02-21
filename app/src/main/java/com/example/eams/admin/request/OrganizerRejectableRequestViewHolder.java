package com.example.eams.admin.request;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.eams.R;

/**
 * A ViewHolder for an individual Organizer request that can be rejected
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerRejectableRequestViewHolder extends OrganizerRequestViewHolder implements RejectableRequest {
    private RejectableRequest rejecter;

    /**
     * Constructor for OrganizerRejectableRequestViewholder
     * @param itemView View to initialize the ViewHolder
     */
    public OrganizerRejectableRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        rejecter = new RequestRejecter(itemView);
        setTvFirstName(R.id.organizer_request_first_name);
        setTvLastName(R.id.organizer_request_last_name);
        setTvPhoneNumber(R.id.organizer_request_phone_number);
        setTvEmail(R.id.organizer_request_email);
        setTvStreet(R.id.organizer_request_street);
        setTvCity(R.id.organizer_request_city);
        setTvProvince(R.id.organizer_request_province);
        setTvPostalCode(R.id.organizer_request_postal_code);
        setTvOrganization(R.id.organizer_request_organization);
        setBtnAccept(R.id.organizer_request_accept);
        setBtnReject(R.id.organizer_request_reject);
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
