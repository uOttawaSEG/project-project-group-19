package com.example.eams.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.example.eams.attendee.AttendeeWelcomeActivity;

/**
 * OrganizerWelcomeActivity welcomes an Organizer with text
 * Bi-directional connection to MainActivity (home/login page)
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_organizer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to Views
        Button logoffButton = findViewById(R.id.logoffButton);
        Button createNewEventButton = findViewById(R.id.createNewEventButton);

        // go to create event page
        createNewEventButton.setOnClickListener(v -> {
            startActivity(new Intent(OrganizerWelcomeActivity.this, OrganizerCreateEventActivity.class));
        });

        // Returns organizer to login page (MainActivity)
        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
