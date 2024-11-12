package com.example.eams.organizer;

import static com.example.eams.organizer.OrganizerViewEventRegistrationsActivity.INTENT_EXTRA_NAME;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.Query;

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

                holder.setViewRegistrationsOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), OrganizerViewEventRegistrationsActivity.class);
                    intent.putExtra(INTENT_EXTRA_NAME, getRef(position).getKey());
                    startActivity(intent);
                });

                holder.bind(event);

                holder.setDeleteButtonListener(v -> {
                    String eventKey = getRef(position).getKey();
                    new AlertDialog.Builder(getContext())
                            .setTitle("Delete Event")
                            .setMessage("Are you sure you want to delete this event?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                getRef(position).removeValue();
                            })
                            .setNegativeButton("No", null)
                            .show();
                });
                holder.setViewRegistrationsOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), OrganizerViewEventRegistrationsActivity.class);
                    intent.putExtra(INTENT_EXTRA_NAME, getRef(position).getKey());
                    startActivity(intent);
                });
            }

        };

        recyclerView.setAdapter(adapter);
    }

}
