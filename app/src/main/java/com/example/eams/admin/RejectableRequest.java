package com.example.eams.admin;


import android.view.View;

public interface RejectableRequest {
    public void setBtnReject(int id);

    public void setRejectOnClickListener(View.OnClickListener l);
}
