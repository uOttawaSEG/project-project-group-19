package com.example.eams.admin;

import android.os.Bundle;
import android.widget.Button;

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
import com.example.eams.admin.request.AttendeePendingRequestListFragment;
import com.example.eams.admin.request.AttendeeRejectedRequestListFragment;
import com.example.eams.admin.request.OrganizerPendingRequestListFragment;
import com.example.eams.admin.request.OrganizerRejectedRequestListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AdministratorInboxActivity displays the Admin user's inbox, where they can accept or
 * reject registrations from Attendees and Organizers
 * Bi-directional connection to AdministratorWelcomeActivity
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AdministratorInboxActivity extends AppCompatActivity {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrator_inbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.admin_inbox_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.admin_inbox_view_pager);
        Button backButton = findViewById(R.id.btn_administrator_inbox_back);

        /* Adapter decides which fragments are used in each tab */
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

        /* The TabLayoutMediator handles the naming of the tabs
        * and interactions between the ViewPager and TabLayout */
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pending Attendee");
                    break;
                case 1:
                    tab.setText("Rejected Attendee");
                    break;
                case 2:
                    tab.setText("Pending Organizer");
                    break;
                case 3:
                    tab.setText("Rejected Organizer");
                    break;
            }
        }).attach();

        // Returns to Admin Welcome Page
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}