package com.example.eams.organizer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.event.Event;
import com.example.eams.event.EventViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class OrganizerEventsListFragment extends Fragment {

    private final Query eventsReference;


    /**
     * Constructor for UpcomingViewEvent
     *
     * @param eventsReference a reference to the node representing the event
     */
    public OrganizerEventsListFragment(Query eventsReference) {
        this.eventsReference = eventsReference;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachRecyclerViewAdapter(view);
    }

    /**
     * Creates FirebaseRecyclerOptions to retrieve data from Firebase
     */
    public FirebaseRecyclerOptions<Event> getFirebaseRecyclerOptions(Query eventRef) {
        return new FirebaseRecyclerOptions.Builder<Event>()
                .setLifecycleOwner(this)
                .setQuery(eventRef, Event.class)
                .build();
    }

    /**
     * Attaches the FirebaseRecyclerAdapter to the view's RecyclerView
     *
     * @param view the View containing the RecyclerView
     */
    private void attachRecyclerViewAdapter(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_recycler_view);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<Event> firebaseRecyclerOptions = getFirebaseRecyclerOptions(eventsReference);

        FirebaseRecyclerAdapter<Event, EventViewHolder> adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(firebaseRecyclerOptions) {
            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event, parent, false);
                return new EventViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event event) {

                String eventKey = event.getDatabaseKey();

                holder.setViewRegistrationsOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), OrganizerViewEventRegistrationsActivity.class);
                    intent.putExtra("eventDatabaseKey", eventKey);
                    startActivity(intent);
                });

                holder.bind(event);
                holder.setDeleteButtonListener(v -> {
                    // check if approved event registrations exist
                    checkAndDeleteEvent(eventKey, position);
                });
            }

            public void checkAndDeleteEvent(String eventKey, int position) {
                DatabaseReference eventDatabaseReference = FirebaseDatabase.getInstance().getReference("events/" + eventKey + "/registeredAttendees");
                eventDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exists = false;
                        for (DataSnapshot registrationSnapshot : snapshot.getChildren()) {
                            if (Objects.equals(registrationSnapshot.getValue(), "approved")) {
                                exists = true; // there are approved attendees
                                break;
                            }
                        }
                        if (exists) { // if there are approved attendees
                            // if they do exist, we can't delete!
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Cannot Delete Event")
                                    .setMessage("This event has registered attendees.")
                                    .setNegativeButton("Okay", null)
                                    .show();
                        } else {
                            // show delete dialog
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Delete Event")
                                    .setMessage("Are you sure you want to delete this event?")
                                    .setPositiveButton("Yes", (dialog, which) -> {
                                        getRef(position).removeValue();
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

        };

        recyclerView.setAdapter(adapter);
    }

}
