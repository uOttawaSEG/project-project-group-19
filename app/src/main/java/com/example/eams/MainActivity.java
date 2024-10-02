package com.example.eams;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.admin.AdminLoginActivity;
import com.example.eams.attendee.AttendeeLoginActivity;
import com.example.eams.organizer.OrganizerLoginActivity;

public class MainActivity extends AppCompatActivity {
    Button attendeeLoginButton;
    Button organizerLoginButton;
    Button adminLoginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Find all the buttons by their IDs */
        attendeeLoginButton = findViewById(R.id.button_attendee_login);
        organizerLoginButton = findViewById(R.id.button_organizer_login);
        adminLoginButton = findViewById(R.id.button_admin_login);

        /*
        Links the buttons to their corresponding activities
        attendeeLoginButton  -> AttendeeLoginActivity
        organizerLoginButton -> OrganizerLoginActivity
        adminLoginButton     -> AdminLoginActivity
         */
        attendeeLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AttendeeLoginActivity.class);
            startActivity(intent);
        });

        organizerLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrganizerLoginActivity.class);
            startActivity(intent);
        });

        adminLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}