package com.example.eams.organizer;

import android.content.Intent;
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
import com.example.eams.users.Organizer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDate;

/**
 * OrganizerViewEvents allows Organizer to view the list of events they created.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerViewEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_view_events);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to views
        TabLayout tabLayout = findViewById(R.id.organizer_view_events_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.organizer_view_events_pager);

        // Get reference to database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventsReference = databaseReference.child("events");

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Query eventsByDateQuery = eventsReference.orderByChild("date");
                String date = LocalDate.now().toString();
                switch (position) {
                    // Past
                    case 1:
                        return new OrganizerEventsListFragment(eventsByDateQuery.endBefore(date));
                    // Upcoming
                    case 0:
                    default:
                        return new OrganizerEventsListFragment(eventsByDateQuery.startAt(date));
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        /* The TabLayoutMediator handles the naming of the tabs
         * and interactions between the ViewPager and TabLayout */
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Upcoming Events");
                    break;
                case 1:
                    tab.setText("Past Events");
                    break;
            }
        }).attach();
    }
}