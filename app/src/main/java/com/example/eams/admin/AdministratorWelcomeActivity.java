package com.example.eams.admin;

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
 * AdministratorWelcomeActivity welcomes the Admin user with text
 * Bi-directional connection to MainActivity (home/login page)
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AdministratorWelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Log off button returns Admin user to login page (MainActivity)
        View logoffButton = findViewById(R.id.logoffButton);

        logoffButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdministratorWelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // View Inbox button sends Admin user to inbox page (admin_inbox)
        View viewInboxButton = findViewById(R.id.viewInboxButton);

        viewInboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdministratorWelcomeActivity.this, AdministratorInboxActivity2.class);
            startActivity(intent);
        });

    }
}
