package com.example.eams.attendee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * AttendeeWelcomeActivity welcomes an Attendee user with text.
 * Bi-directional connection to MainActivity (home/login page).
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AttendeeWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String userDatabaseKey = intent.getStringExtra("userDatabaseKey");

        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_attendee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to views
        Button logoffButton = findViewById(R.id.logoffButton);
        Button searchButton = findViewById(R.id.attendee_search_page_button);
        Button viewEventsButton = findViewById(R.id.attendee_view_events_button);
        TextView firstNameText = findViewById(R.id.firstNameText);
        TextView lastNameText = findViewById(R.id.lastNameText);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users/attendees/approved/" + userDatabaseKey);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue(String.class);
                String lastName = snapshot.child("lastName").getValue(String.class);
                if (firstName != null) {
                    firstNameText.setText(firstName);
                }
                if(lastName != null){
                    lastNameText.setText(lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Returns attendee to login page (MainActivity)
        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchButton.setOnClickListener(v -> {
            Intent searchIntent = new Intent(this, AttendeeEventSearchActivity.class);
            startActivity(searchIntent);
        });

        viewEventsButton.setOnClickListener(v -> {
            Intent viewIntent = new Intent(this, AttendeeViewEventsActivity.class);
            viewIntent.putExtra("userDatabaseKey", userDatabaseKey);
            startActivity(viewIntent);
        });

    }
}
