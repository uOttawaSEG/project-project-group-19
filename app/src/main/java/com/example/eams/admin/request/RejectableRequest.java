package com.example.eams.admin.request;


import android.view.View;

/**
 * An interface for a request that can be rejected; i.e., a pending request
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
interface RejectableRequest {
    /**
     * Finds the Button in the layout
     * @param id the id of the button
     */
    public void setBtnReject(int id);

    /**
     * Sets the OnClickListener of the button
     * @param l the OnClickListener to be set
     */
    public void setRejectOnClickListener(View.OnClickListener l);
}
