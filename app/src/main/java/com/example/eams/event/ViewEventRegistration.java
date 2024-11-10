package com.example.eams.event;

import android.view.View;

/**
 * An interface for an event that can be viewed by an organizer
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public interface ViewEvent {

    /**
     * Finds the Button in the layout
     * @param id the id of the button
     */
    public void setBtnViewEvents(int id);

    /**
     * Sets the OnClickListener of the button
     * @param l the OnClickListener to be set
     */
    public void setViewEventsOnClickListener(View.OnClickListener l);
}


