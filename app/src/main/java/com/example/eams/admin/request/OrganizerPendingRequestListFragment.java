package com.example.eams.admin.request;

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
import com.example.eams.users.Organizer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

/**
 * A Fragment for the Organizer pending request tab
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerPendingRequestListFragment extends Fragment implements RequestAdapterCreator<Organizer, OrganizerRejectableRequestViewHolder> {
    private final String requestType = "pending";
    private DatabaseReference userTypeReference;

    /**
     * Constructor for OrganizerPendingRequestListFragment
     * @param userTypeRef  a reference to the node representing the user's type.
     *                     Should be "users/attendees" or "users/organizers"
     */
    public OrganizerPendingRequestListFragment(DatabaseReference userTypeRef) {
        this.userTypeReference = userTypeRef;
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

    @Override
    public FirebaseRecyclerOptions<Organizer> getFirebaseRecyclerOptions(DatabaseReference userTypeRef) {
        return new FirebaseRecyclerOptions.Builder<Organizer>()
                .setLifecycleOwner(this)
                .setQuery(userTypeRef.child(requestType), Organizer.class)
                .build();
    }

    @Override
    public OrganizerRejectableRequestViewHolder initViewHolder(View view) {
        return new OrganizerRejectableRequestViewHolder(view);
    }

    /**
     * Attaches the RecyclerViewAdapter to the view's RecyclerView
     * @param view the View containing the RecyclerView
     */
    private void attachRecyclerViewAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.fragment_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(getRequestAdapter(
                userTypeReference,
                requestType,
                R.layout.recyclerview_organizer_pending_request));
    }
}
