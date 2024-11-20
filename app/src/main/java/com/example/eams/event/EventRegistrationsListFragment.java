package com.example.eams.event;

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

import com.example.eams.R;
import com.example.eams.organizer.WrapContentLinearLayoutManager;
import com.example.eams.users.Attendee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A Fragment for the Attendee pending request tab.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class EventRegistrationsListFragment extends Fragment {


    private final Query eventAttendeesReference;
    private String eventKey;

    /**
     * Constructor for PendingAttendeeEventRegistration View
     *
     * @param eventAttendeesReference a reference to the node representing the event
     */
    public EventRegistrationsListFragment(Query eventAttendeesReference, String eventKey) {
        this.eventAttendeesReference = eventAttendeesReference;
        this.eventKey = eventKey;
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
     *
     * @param eventAttendeesReference
     */
    public FirebaseRecyclerOptions<String> getFirebaseRecyclerOptions(Query eventAttendeesReference) {
        return new FirebaseRecyclerOptions.Builder<String>()
                .setLifecycleOwner(this)
                .setQuery(eventAttendeesReference, String.class)
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

        FirebaseRecyclerOptions<String> firebaseRecyclerOptions = getFirebaseRecyclerOptions(eventAttendeesReference);

        FirebaseRecyclerAdapter<String, EventRegistrationAttendeeViewHolder> adapter = new FirebaseRecyclerAdapter<String, EventRegistrationAttendeeViewHolder>(firebaseRecyclerOptions) {
            @NonNull
            @Override
            public EventRegistrationAttendeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_organizer_registered_event_attendees, parent, false);
                return new EventRegistrationAttendeeViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventRegistrationAttendeeViewHolder holder, int position, @NonNull String attendeeKey) {
                String attendeeKeyCorrect = getRef(position).getKey();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("users/attendees/approved").child(attendeeKeyCorrect).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("Firebase", "Error getting attendee");
                        return;
                    }

                    Attendee attendee = task.getResult().getValue(Attendee.class);
                    if (attendee != null) {
                        holder.setBtnViewAttendeeDetailsOnClickListener(v -> {
                            EventDialogFragment dialog = new EventDialogFragment(attendee, eventKey);
                            dialog.show(requireActivity().getSupportFragmentManager(), "attendeeDetails");
                        });
                        holder.bind(attendee);
                    } else {
                        Log.e("Attendee", "Attendee is null");
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}