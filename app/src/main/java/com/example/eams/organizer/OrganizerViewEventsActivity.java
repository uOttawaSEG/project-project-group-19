package com.example.eams.organizer;

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
import com.example.eams.event.ViewEventListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class OrganizerViewEventsActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    // get database reference to events
    private DatabaseReference eventsRef = database.getReference("events");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_view_events);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize refs to views
        TabLayout tabLayout = findViewById(R.id.organizer_view_events_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.organizer_view_events_pager);
        Button backButton = findViewById(R.id.btn_organizer_view_events_back);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                String date = LocalDate.now().toString();
                switch (position) {
                case 0:
                    return new ViewEventListFragment(eventsRef.orderByChild("date").startAt(date));
                case 1:
                    return new ViewEventListFragment(eventsRef.orderByChild("date").endBefore(date));
                default:
                    return new ViewEventListFragment(eventsRef.orderByChild("date").startAt(date));
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

        // Returns to Organizer Welcome Page
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}