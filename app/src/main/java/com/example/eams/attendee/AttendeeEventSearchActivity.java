package com.example.eams.attendee;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.event.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;

public class AttendeeEventSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee_event_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Event> events = new ArrayList<Event>();
        EditText searchText = findViewById(R.id.event_searchbar);
        Button searchButton = findViewById(R.id.event_search_button);
        RecyclerView recyclerView = findViewById(R.id.event_search_rv);

        EventSearchAdapter adapter = new EventSearchAdapter(events, getIntent().getStringExtra("UserKey"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        String userDatabaseKey = getIntent().getStringExtra("userDatabaseKey");

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("events");

        searchButton.setOnClickListener(v -> {
            String search = String.valueOf(searchText.getText()).trim();

            /* Query for all events */
            db.orderByKey().get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("FIREBASE", "Failed to search events");
                    return;
                }

                DataSnapshot dataSnapshot = task.getResult();
                Iterator<DataSnapshot> eventIterator = dataSnapshot.getChildren().iterator();
                Event event;
                DataSnapshot curSnap;
                ArrayList<Event> newEvents = new ArrayList<>();

                /* Loop through events and find the ones that contain the search and that are not past events */
                while (eventIterator.hasNext()) {
                    curSnap = eventIterator.next();
                    event = curSnap.getValue(Event.class);

                    if ((event.getTitle().contains(search)
                            || event.getDescription().contains(search))
                            && !event.isPastEvent()) {
                        newEvents.add(event);
                    }
                }

                adapter.setEvents(newEvents);
            });
        });
    }
}