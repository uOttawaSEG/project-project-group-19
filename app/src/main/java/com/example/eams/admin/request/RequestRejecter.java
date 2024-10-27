package com.example.eams.admin.request;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

/**
 * A concrete implementation of a RejectableRequest
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
