//
//package com.example.eams.event;
//
//
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//
///**
// * Concrete implementation of ViewEvent
// *
// * @author Alex Ajersch
// * @author Brooklyn McClelland
// * @author Moïse Kenge Ngoyi
// * @author Naomi Braun
// * @author Rachel Qi
// * @author Steven Wu
// */
//public class EventRegistrationViewer{
//
//    private Button btnViewRegistrations;
//    private View itemView;
//
//    /**
//     * Constructor for EventViewer
//     * @param itemView the View that contains the "View Events" button
//     */
//    public EventRegistrationViewer(@NonNull View itemView) {
//        this.itemView = itemView;
//    }
//
//    public void setBtnViewRegistrations(int id) {
//        this.btnViewRegistrations = itemView.findViewById(id);
//    }
//
//    public void setViewRegistrationsOnClickListener(View.OnClickListener l) {
//
//        if (btnViewRegistrations == null) {
//            return;
//        }
//
//        btnViewRegistrations.setOnClickListener(l);
//    }
//
//}
