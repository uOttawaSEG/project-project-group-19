package com.example.eams.attendee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;

/**
 * AttendeeRegisterActivity allows a User to register as an Attendee
 * Bi-directional connection to MainActivity (home/login page)
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AttendeeRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            // Confirm button from registration page directs User back to login page (MainActivity)

            View confirmButtonAttendee = findViewById(R.id.confirm_button);

            confirmButtonAttendee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Redirect to Attendee's welcome screen
                    Intent intent = new Intent(AttendeeRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

    }
}