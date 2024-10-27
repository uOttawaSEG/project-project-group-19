package com.example.eams.admin;

import android.os.Bundle;
import android.util.Log;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdministratorInboxActivity2 extends AppCompatActivity /*implements RequestAdapterCreator<Attendee, AttendeeRequestViewHolder> */{
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");

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

        TabLayout tabLayout = findViewById(R.id.admin_inbox_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.admin_inbox_view_pager);
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new AttendeePendingRequestListFragment(userRef.child("attendees"));
                    case 1:
                        return new AttendeeRejectedRequestListFragment(userRef.child("attendees"));
                    case 2:
                        return new OrganizerPendingRequestListFragment(userRef.child("organizers"));
                    case 3:
                        return new OrganizerRejectedRequestListFragment(userRef.child("organizers"));
                    default:
                        return new AttendeePendingRequestListFragment(userRef.child("attendees"));
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pending Att");
                    break;
                case 1:
                    tab.setText("Rejected Att");
                    break;
                case 2:
                    tab.setText("Pending Org");
                    break;
                case 3:
                    tab.setText("Rejected Org");
                    break;
            }
        }).attach();



//        attachRecyclerViewAdapter();
    }

//    private void attachRecyclerViewAdapter() {
//        RecyclerView rv = findViewById(R.id.rv_attendee_pending_requests);
//        rv.setLayoutManager(new LinearLayoutManager((this)));
//        rv.setAdapter(getRequestAdapter(userTypeRef, R.layout.recyclerview_attendee_rejected_request));
//    }
//
//    @Override
//    public FirebaseRecyclerOptions<Attendee> getFirebaseRecyclerOptions(DatabaseReference userTypeRef) {
//        return new FirebaseRecyclerOptions.Builder<Attendee>()
//                .setLifecycleOwner(this)
//                .setQuery(userTypeRef.child("rejected"), Attendee.class)
//                .build();
//    }
//
//    @Override
//    public AttendeeRequestViewHolder initViewHolder(View view) {
//        return new AttendeeRequestViewHolder(view);
//    }
}