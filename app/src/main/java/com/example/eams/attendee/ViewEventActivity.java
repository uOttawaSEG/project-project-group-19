package com.example.eams.attendee;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.eams.R;
import com.example.eams.event.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewEventActivity extends AppCompatActivity {

    Event event;

    public interface ConflictCheckCallback {
        void onConflictDetected(boolean hasConflict);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String eventKey = getIntent().getStringExtra("EventKey");
        String attendeeKey = getIntent().getStringExtra("userDatabaseKey");

        TextView title = findViewById(R.id.view_event_search_title);
        TextView desc = findViewById(R.id.view_event_search_description);
        TextView date = findViewById(R.id.view_event_date);
        TextView start = findViewById(R.id.view_event_start_time);
        TextView end = findViewById(R.id.view_event_end_time);
        TextView street = findViewById(R.id.view_event_street);
        TextView city = findViewById(R.id.view_event_city);
        TextView province = findViewById(R.id.view_event_province);
        TextView postal = findViewById(R.id.view_event_postal_code);
        Button registerButton = findViewById(R.id.view_event_register_button);

        FirebaseDatabase.getInstance()
                .getReference("events")
                .child(eventKey)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("FIREBASE", "Unable to get event in ViewEventActivity");
                        return;
                    }
                    event = task.getResult().getValue(Event.class);

                    if (event == null) {
                        Log.e("FIREBASE", "event is null :(");
                        return;
                    }
                    title.setText(event.getTitle());
                    desc.setText(event.getDescription());
                    date.setText(event.getDate());
                    start.setText(event.getStartTime());
                    end.setText(event.getEndTime());
                    street.setText(event.getStreet());
                    city.setText(event.getCity());
                    province.setText(event.getProvince());
                    postal.setText(event.getPostalCode());
                });

        registerButton.setOnClickListener(v -> {
            //registerForEvent(attendeeKey, eventKey);

            checkForConflict(event,attendeeKey, hasConflict -> {
                if (hasConflict) {
                    new AlertDialog.Builder(this)
                            .setTitle("Registration Unsuccessful")
                            .setMessage("You have already registered for an event at the same time")
                            .setNegativeButton("Okay", null)
                            .show();
                } else {
                    registerForEvent(attendeeKey, eventKey);
                    Toast.makeText(this, "Successfully registered for " + event.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    /*
     * Registers the attendee for the event
     * */
    private void registerForEvent(String attendeeKey, String eventKey) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> eventData = new HashMap<>();
        eventData.put(attendeeKey, "pending");
        db.child("events")
                .child(eventKey)
                .child("registeredAttendees")
                .updateChildren(eventData);

        Map<String, Object> attendeeData = new HashMap<>();
        attendeeData.put(eventKey, "pending");
        db.child("users/attendees/approved")
                .child(attendeeKey)
                .child("eventsRegisteredTo")
                .updateChildren(attendeeData);
    }

    private void checkForConflict(Event event, String attendeeKey, ConflictCheckCallback callback) {
        Log.e("AHHHHHHHHHHHHHHHHHH", "128");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        // get the attendee's registered events
        db.child("users/attendees/approved")
                .child(attendeeKey)
                .child("eventsRegisteredTo")
                .get()
                .addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        Log.e("FIREBASE", "Unable to get registered events in ViewEventActivity");
                        callback.onConflictDetected(true);
                        return;
                    }


                    Map<String, Event> registeredEvents = (Map<String, Event>) task1.getResult().getValue();

                    DataSnapshot snapshot = task1.getResult();
                    Log.d("FIREBASE", "Raw registeredEvent Data: " + snapshot.getValue());
                    Log.d("FIREBASE", "Raw registeredEvent Data: " + registeredEvents);

                    // there is no conflict if the attendee has no other registered events
                    if (registeredEvents == null) {
                        Log.e("AHHHHHHHHHHHHHHHHHH", "146");
                        callback.onConflictDetected(false);
                        return;
                    }

                    AtomicBoolean conflict = new AtomicBoolean(false);
                    final int[] remainingEvents = {registeredEvents.size()}; // Track remaining events to be checked

                    // loop through each registered event key and compare date/time
                    for (String registeredEventKey : registeredEvents.keySet()) {
                        db.child("events")
                                .child(registeredEventKey)
                                .get()
                                .addOnCompleteListener(task2 -> {
                                    if (conflict.get() == true) return;

                                    if (!task2.isSuccessful()) {
                                        Log.e("FIREBASE", "Unable to get registered events in ViewEventActivity");
                                        callback.onConflictDetected(true);
                                        return;
                                    }

                                    Event registeredEvent = task2.getResult().getValue(Event.class);

                                    DataSnapshot snapshot2 = task2.getResult();
                                    Log.d("AHHHHHHHHHH", "Raw Event Data: " + snapshot2.getValue());


                                    // TODO: this should go outside of this loop because here it is bascially returning the result multiple times
                                    // now we compare event to registeredEvent to check if we can register
                                    if (event.getDate().equals(registeredEvent.getDate())) {
                                        Log.e("AHHHHHHHHHHHHHHHHHH", "events are the same day");

                                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());

                                        LocalTime eventStartTime = LocalTime.parse(event.getStartTime(), timeFormatter);
                                        LocalTime eventEndTime = LocalTime.parse(event.getEndTime(), timeFormatter);

                                        LocalTime registeredEventStartTime = LocalTime.parse(registeredEvent.getStartTime(), timeFormatter);
                                        LocalTime registeredEventEndTime = LocalTime.parse(registeredEvent.getEndTime(), timeFormatter);

                                        // check if there is an overlap
                                        if (registeredEventStartTime.isBefore(eventEndTime) && eventStartTime.isBefore(registeredEventEndTime)) {
                                            conflict.set(true);
                                            callback.onConflictDetected(true);
                                            Log.e("AHHHHHHHHHHHHHHHHHH", "time overlap");
                                        }
                                        else {
                                            conflict.set(false);
                                        }
                                    } else {
                                        conflict.set(false);
                                        Log.e("AHHHHHHHHHHHHHHHHHH", "events are different days");
                                    }

                                    // Decrement remaining events and check if comparing is done
                                    remainingEvents[0]--;
                                    if (remainingEvents[0] == 0 && conflict.get() == false) { // if there are no remaining events to check, and there are no conflicts
                                        callback.onConflictDetected(false);
                                    }

                                });
                    }
                });
    }
}
