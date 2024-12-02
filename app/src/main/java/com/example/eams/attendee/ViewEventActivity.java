package com.example.eams.attendee;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.eams.R;
import com.example.eams.event.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ViewEventActivity extends AppCompatActivity {

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
        String attendeeKey = getIntent().getStringExtra("UserKey");

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
                    Event event = task.getResult().getValue(Event.class);

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
            Map<String, Object> eventData = new HashMap<>();
            eventData.put(attendeeKey, "pending");
            FirebaseDatabase.getInstance()
                    .getReference("events")
                    .child(eventKey)
                    .child("registeredAttendees")
                    .updateChildren(eventData);

            Map<String, Object> attendeeData = new HashMap<>();
            attendeeData.put(eventKey, "pending");
            FirebaseDatabase.getInstance()
                    .getReference("users/attendees/approved")
                    .child(attendeeKey)
                    .child("eventsRegisteredTo")
                    .updateChildren(attendeeData);
        });
    }
}