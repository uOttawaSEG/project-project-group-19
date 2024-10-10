package com.example.eams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.attendee.AttendeeRegisterActivity;
import com.example.eams.attendee.AttendeeWelcomeActivity;
import com.example.eams.organizer.OrganizerRegisterActivity;
import com.example.eams.organizer.OrganizerWelcomeActivity;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Database variables
    private DatabaseReference databaseRef;
    private String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to Views
        Spinner spinner = findViewById(R.id.userSelectSpinner); // For selecting user type
        Button registerButton = findViewById(R.id.registerButton); // For Going to registration page for attendee/organizer
        Button loginButton = findViewById(R.id.loginButton); // For attempting to process login

        // Set up spinner to choose from Administrator, Organizer, and Attendee
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // When the user presses the registerButton
        registerButton.setOnClickListener(v -> {
            switch (userType) {
                case "attendee": {
                    Intent intent = new Intent(MainActivity.this, AttendeeRegisterActivity.class);
                    startActivity(intent);
                    break;
                }
                case "organizer": {
                    Intent intent = new Intent(MainActivity.this, OrganizerRegisterActivity.class);
                    startActivity(intent);
                    break;
                }
                case "administrator":
                    CharSequence text = "No registration needed. Proceed to login ";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
            }
        });

        // When the user presses the loginButton
        loginButton.setOnClickListener(v -> {
            Intent intent = null;
            switch (userType) {
                case "attendee": {
                    intent = new Intent(MainActivity.this, AttendeeWelcomeActivity.class);
                    break;
                }
                case "organizer": {
                    intent = new Intent(MainActivity.this, OrganizerWelcomeActivity.class);
                    break;
                }
                case "administrator": {
                    intent = new Intent(MainActivity.this, AdministratorWelcomeActivity.class);
                }
            }
            if (intent != null) {
                startActivity(intent);
            }
        });


        // Get reference of data of correct user type
        //databaseRef = FirebaseDatabase.getInstance().getReference(userType);

    }


    // When the user selects an item on the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        userType = ((String) parent.getItemAtPosition(pos)).toLowerCase();
        switch (userType) {
            case "administrator": {
                CharSequence text = "I'm an Administrator!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                break;
            }
            case "organizer": {
                CharSequence text = "I'm an Organizer!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                break;
            }
            case "attendee": {
                CharSequence text = "I'm an Attendee!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                break;
            }
            default: {
                CharSequence text = "Must select a user type!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        }

    }

    // Unused interface callback
    public void onNothingSelected(AdapterView<?> parent) {
    }
}