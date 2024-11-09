package com.example.eams.event;


import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

/**
 * Concrete implementation of ViewEvent
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class EventViewer implements ViewEvent {

    private Button btnViewEvents;
    private View itemView;

    /**
     * Constructor for EventViewer
     * @param itemView the View that contains the "View Events" button
     */
    public EventViewer(@NonNull View itemView) {
        this.itemView = itemView;
    }

    @Override
    public void setBtnViewEvents(int id) {
        this.btnViewEvents = itemView.findViewById(id);
    }

    @Override
    public void setViewEventsOnClickListener(View.OnClickListener l) {
        if (btnViewEvents == null) {
            return;
        }
        btnViewEvents.setOnClickListener(l);
    }

}
