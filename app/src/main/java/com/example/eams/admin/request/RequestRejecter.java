package com.example.eams.admin.request;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

/**
 * Concrete implementation of RejectableRequest
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
class RequestRejecter implements RejectableRequest {
    private Button btnReject;
    private View itemView;

    /**
     * Constructor for RequestRejecter
     * @param itemView the View that contains the "Reject" button
     */
    public RequestRejecter(@NonNull View itemView) {
        this.itemView = itemView;
    }

    @Override
    public void setBtnReject(int id) {
        this.btnReject = itemView.findViewById(id);
    }

    @Override
    public void setRejectOnClickListener(View.OnClickListener l) {
        if (btnReject == null) {
            return;
        }
        btnReject.setOnClickListener(l);
    }
}
