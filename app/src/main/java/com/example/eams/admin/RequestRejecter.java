package com.example.eams.admin;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

class RequestRejecter implements RejectableRequest {
    private Button btnReject;
    private View itemView;

    public RequestRejecter(@NonNull View itemView) {
        this.itemView = itemView;
    }

    public void setBtnReject(int id) {
        this.btnReject = itemView.findViewById(id);
    }

    public void setRejectOnClickListener(View.OnClickListener l) {
        if (btnReject == null) {
            return;
        }
        btnReject.setOnClickListener(l);
    }
}
