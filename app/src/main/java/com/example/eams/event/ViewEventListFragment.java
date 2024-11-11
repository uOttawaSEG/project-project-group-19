package com.example.eams.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.example.eams.organizer.OrganizerViewEventRegistrationsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ViewEventListFragment extends Fragment {

    private Query eventReference;
    private FirebaseRecyclerAdapter<Event, EventViewHolder> adapter;

    /**
     * Constructor for UpcomingViewEvent
     *
     * @param eventRef a reference to the node representing the event
     */
    public ViewEventListFragment(Query eventRef) {
        this.eventReference = eventRef;
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
        RecyclerView rv = view.findViewById(R.id.fragment_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Event> options = getFirebaseRecyclerOptions(eventReference);

        adapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {
            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event, parent, false);
                return new EventViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event event) {

                holder.setViewRegistrationsOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), OrganizerViewEventRegistrationsActivity.class);
                        intent.putExtra("pushKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.bind(event);

            }

        };

        rv.setAdapter(adapter);
    }

}
