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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.eams.R;
import com.example.eams.event.EventRegistration;
import com.example.eams.event.EventRegistrationViewHolder;
import com.example.eams.event.ViewEventListFragment;
import com.example.eams.event.ViewEventRegistrationListFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDate;

public class OrganizerViewEventRegistrationsActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<EventRegistration, EventRegistrationViewHolder> adapter;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    // get database reference to event registrations
    private DatabaseReference eventsRef = database.getReference("registrations");

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
        ViewEventRegistrationListFragment
        Button backButton = findViewById(R.id.btn_organizer_view_event_registrations_back);

        // Returns to Organizer View Events Activity
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * Creates FirebaseRecyclerOptions to retrieve data from Firebase
     */
    public FirebaseRecyclerOptions<EventRegistration> getFirebaseRecyclerOptions(Query eventRegistrationRef) {
        return new FirebaseRecyclerOptions.Builder<EventRegistration>()
                .setLifecycleOwner(this)
                .setQuery(eventRegistrationRef, EventRegistration.class)
                .build();
    }

    /**
     * Attaches the FirebaseRecyclerAdapter to the view's RecyclerView
     * @param view the View containing the RecyclerView
     */
    private void attachRecyclerViewAdapter(View view) {

        RecyclerView rv = view.findViewById(R.id.fragment_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<EventRegistration> options = getFirebaseRecyclerOptions(eventsRef);

        adapter = new FirebaseRecyclerAdapter<EventRegistration, EventRegistrationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventRegistrationViewHolder holder, int position, @NonNull EventRegistration eventRegistration) {

                holder.bind(eventRegistration);
            }

            @NonNull
            @Override
            public EventRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event_registration_attendees, parent, false);
                return new EventRegistrationViewHolder(itemView);
            }
        };

        rv.setAdapter(adapter);
    }
}