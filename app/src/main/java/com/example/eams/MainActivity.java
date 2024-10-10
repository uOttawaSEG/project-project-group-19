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
    // Views
    private Button loginButton;
    private Button registerButton;

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

        // Set up dropdown menu to choose from Administrator, Organizer, and Attendee
        Spinner spinner = (Spinner) findViewById(R.id.userSelectSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Register button to bring to registration page for attendee or organizer
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("attendee")){
                    Intent intent = new Intent(MainActivity.this, AttendeeRegisterActivity.class);
                    startActivity(intent);
                }
                else if (userType.equals("organizer")){
                    Intent intent = new Intent(MainActivity.this, OrganizerRegisterActivity.class);
                    startActivity(intent);
                }
                else if (userType.equals("administrator")) {
                    CharSequence text = "No registration needed. Proceed to login ";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
            }


        });

        //Login to take to welcome page

        View LoginButton = findViewById(R.id.loginButton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("attendee")){
                    Intent intent = new Intent(MainActivity.this, AttendeeWelcomeActivity.class);
                    startActivity(intent);
                }
                else if (userType.equals("organizer")){
                    Intent intent = new Intent(MainActivity.this, OrganizerWelcomeActivity.class);
                    startActivity(intent);
                }
                else if (userType.equals("administrator")) {
                    Intent intent = new Intent(MainActivity.this, AdministratorWelcomeActivity.class);
                    startActivity(intent);
                }
            }


        });


        // Get reference of data of correct user type
        //databaseRef = FirebaseDatabase.getInstance().getReference(userType);

    }

    //Validating sign in





    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        userType = ((String) parent.getItemAtPosition(pos)).toLowerCase();
        if (userType.equals("administrator")) {
            CharSequence text = "I'm an Administrator!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else if (userType.equals("organizer")) {
            CharSequence text = "I'm an Organizer!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else if (userType.equals("attendee")) {
            CharSequence text = "I'm an Attendee!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else {
            CharSequence text = "Must select a user type!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}