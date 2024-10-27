package com.example.eams.admin;

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

public class OrganizerRejectedRequestListFragment extends Fragment implements RequestAdapterCreator<Organizer, OrganizerRequestViewHolder> {
    private final String requestType = "rejected";
    private DatabaseReference userTypeReference;

    public OrganizerRejectedRequestListFragment(DatabaseReference userTypeRef) {
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
    public OrganizerRequestViewHolder initViewHolder(View view) {
        return new OrganizerRequestViewHolder(view);
    }

    private void attachRecyclerViewAdapter(View view) {
        RecyclerView rv = view.findViewById(R.id.fragment_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(getRequestAdapter(
                userTypeReference,
                requestType,
                R.layout.recyclerview_organizer_rejected_request));
    }
}