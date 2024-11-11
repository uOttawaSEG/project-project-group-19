package com.example.eams.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.event.EventDialogFragment;
import com.example.eams.event.EventRegistrationAttendeeViewHolder;
import com.example.eams.users.Attendee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OrganizerViewEventRegistrationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_view_event_registrations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize refs to views
        RecyclerView recyclerView = findViewById(R.id.rv_organizer_view_event_registrations);
        Button backButton = findViewById(R.id.btn_organizer_view_event_registrations_back);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/attendees/approved");

        String eventKey = getIntent().getStringExtra("pushKey");
//        Query registeredAttendeesQuery = databaseReference.orderByChild("attendeePendingEventRegistrations").equalTo(eventKey);
        Query registeredAttendeesQuery = databaseReference;
        attachRecyclerViewAdapter(recyclerView, registeredAttendeesQuery, eventKey);

        // Returns to Organizer View Events Activity
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * Creates FirebaseRecyclerOptions to retrieve data from Firebase
     */
    public FirebaseRecyclerOptions<Attendee> getFirebaseRecyclerOptions(Query registeredAttendeesQuery) {
        return new FirebaseRecyclerOptions.Builder<Attendee>()
                .setLifecycleOwner(this)
                .setQuery(registeredAttendeesQuery, Attendee.class)
                .build();
    }

    /**
     * Attaches the FirebaseRecyclerAdapter to the view's RecyclerView
     *
     * @param recyclerView RecyclerView to receive the adapter
     */
    private void attachRecyclerViewAdapter(RecyclerView recyclerView, Query registeredAttendeesQuery, String eventKey) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Attendee> options = getFirebaseRecyclerOptions(registeredAttendeesQuery);

        FirebaseRecyclerAdapter<Attendee, EventRegistrationAttendeeViewHolder> adapter = new FirebaseRecyclerAdapter<Attendee, EventRegistrationAttendeeViewHolder>(options) {

            @NonNull
            @Override
            public EventRegistrationAttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event_registration_attendees, parent, false);
                return new EventRegistrationAttendeeViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventRegistrationAttendeeViewHolder holder, int position, @NonNull Attendee attendee) {
                holder.setBtnViewAttendeeDetailsOnClickListener(v -> {
                    EventDialogFragment dialog = new EventDialogFragment(attendee, eventKey);
                    dialog.show(getSupportFragmentManager(), "attendeeDetails");
                });
                holder.bind(attendee);
            }

        };

        recyclerView.setAdapter(adapter);
    }
}