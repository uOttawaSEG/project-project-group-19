package com.example.eams.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eams.R;
import com.example.eams.event.EventRegistrationsListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * OrganizerViewEventRegistrationsActivity displays the pending and approved Attendees
 * that have registered for an Event.
 *
 * Rejected Attendees are not shown.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerViewEventRegistrationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String eventKey = intent.getStringExtra("eventDatabaseKey");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_view_events_registrations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.organizer_view_event_registrations_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.organizer_view_event_registrations_pager);
        Button approveAllButton = findViewById(R.id.btn_organizer_approve_all_event_registrations);

        /* Adapter decides which fragments are used in each tab */
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Query eventAttendeesQuery = FirebaseDatabase.getInstance().getReference("events" + "/" + eventKey + "/registeredAttendees");

                String requestType = position == 1 ? "approved" : "pending";
                return new EventRegistrationsListFragment(requestType, eventAttendeesQuery.orderByValue().equalTo(requestType), eventKey);
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        /* The TabLayoutMediator handles the naming of the tabs
        * and interactions between the ViewPager and TabLayout */
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pending Registrations");
                    break;
                case 1:
                    tab.setText("Approved Registrations");
                    break;
            }
        }).attach();

        // Approves all attendees
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
        DatabaseReference eventsReference = databaseReference.child("events/"+eventKey);

        eventsReference.child("registeredAttendees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot attendeeSnapshot : dataSnapshot.getChildren()) {
                        String attendeeKey = attendeeSnapshot.getKey();

                        // Change all pending Attendee event registrations to approved
                        if (attendeeSnapshot.getValue(String.class).equals("pending")) {
                            // From attendee side
                            attendeesReference.child(attendeeKey + "/eventsRegisteredTo/" + eventKey).setValue("approved");
                            // From events side
                            eventsReference.child("registeredAttendees/" + attendeeKey).setValue("approved");
                        }
                    }
                    Toast.makeText(OrganizerViewEventRegistrationsActivity.this, "All attendees approved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrganizerViewEventRegistrationsActivity.this, "No attendees to approve.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrganizerViewEventRegistrationsActivity.this, "Error retrieving from Database.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}