package com.example.eams.organizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OrganizerViewEventRegistrationsActivity extends AppCompatActivity {

    public final static String INTENT_EXTRA_NAME = "pushID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_view_event_registrations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to views
        RecyclerView recyclerView = findViewById(R.id.rv_organizer_view_event_registrations);
        Button backButton = findViewById(R.id.btn_organizer_view_event_registrations_back);
        Button approveAllButton = findViewById(R.id.btn_organizer_approval_all_event_registrations);

        // Get reference to database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference approvedAttendeesReference = databaseReference.child("users/attendees/approved");

        // Get the eventKey from the intent
        String eventKey = getIntent().getStringExtra(INTENT_EXTRA_NAME);
        Query pendingAttendees = approvedAttendeesReference.orderByChild("pendingEventRegistrationKeys").equalTo(eventKey);
        attachRecyclerViewAdapter(recyclerView, pendingAttendees, eventKey);

        // Returns to Organizer View Events Activity
        backButton.setOnClickListener(v -> {
            finish();
        });
        //Approves all attendees
        approveAllButton.setOnClickListener(v -> {
            approveAllAttendees(eventKey);
        });
    }
    /**
     * Approves all attendees
     */
    private void approveAllAttendees(String eventKey) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference attendeesReference = databaseReference.child("users/attendees/approved");

        Query pendingAttendeesQuery = attendeesReference.orderByChild("pendingEventRegistrationKeys").equalTo(eventKey);

        pendingAttendeesQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                for (DataSnapshot attendeeSnapshot : task.getResult().getChildren()) {
                    Attendee attendee = attendeeSnapshot.getValue(Attendee.class);

                    if (attendee != null) {
                        attendee.approveEventRegistration(eventKey);

                        attendeesReference.child(attendeeSnapshot.getKey()).setValue(attendee);
                    }
                }
                Toast.makeText(OrganizerViewEventRegistrationsActivity.this, "All attendees approved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OrganizerViewEventRegistrationsActivity.this, "No pending attendees to approve.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Creates FirebaseRecyclerOptions to retrieve data from Firebase
     */
    public FirebaseRecyclerOptions<Attendee> getFirebaseRecyclerOptions(Query pendingAttendees) {
        return new FirebaseRecyclerOptions.Builder<Attendee>()
                .setLifecycleOwner(this)
                .setQuery(pendingAttendees, Attendee.class)
                .build();
    }

    /**
     * Attaches the FirebaseRecyclerAdapter to the view's RecyclerView
     *
     * @param recyclerView RecyclerView to receive the adapter
     */
    private void attachRecyclerViewAdapter(RecyclerView recyclerView, Query registeredAttendeesQuery, String eventKey) {

        // Use a LinearLayout for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Attendee> recyclerOptions = getFirebaseRecyclerOptions(registeredAttendeesQuery);

        FirebaseRecyclerAdapter<Attendee, EventRegistrationAttendeeViewHolder> adapter = new FirebaseRecyclerAdapter<Attendee, EventRegistrationAttendeeViewHolder>(recyclerOptions) {

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