package com.example.eams.admin;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.users.Attendee;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdministratorInboxActivity2 extends AppCompatActivity implements RequestAdapterCreator<Attendee, AttendeeRequestViewHolder> {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userTypeRef = database.getReference("users/attendees");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrator_inbox_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        attachRecyclerViewAdapter();
    }

    private void attachRecyclerViewAdapter() {
        RecyclerView rv = findViewById(R.id.rv_attendee_pending_requests);
        rv.setLayoutManager(new LinearLayoutManager((this)));
        rv.setAdapter(getRequestAdapter(userTypeRef, R.layout.recyclerview_attendee_rejected_request));
    }

    @Override
    public FirebaseRecyclerOptions<Attendee> getFirebaseRecyclerOptions(DatabaseReference userTypeRef) {
        return new FirebaseRecyclerOptions.Builder<Attendee>()
                .setLifecycleOwner(this)
                .setQuery(userTypeRef.child("rejected"), Attendee.class)
                .build();
    }

    @Override
    public AttendeeRequestViewHolder initViewHolder(View view) {
        return new AttendeeRequestViewHolder(view);
    }
}