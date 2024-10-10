package com.example.eams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.eams.users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Possible user types
    private enum UserType {
        INVALID,
        ATTENDEE,
        ORGANIZER,
        ADMINISTRATOR
    }
    // Selected user type
    private UserType userType;


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
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Spinner spinner = findViewById(R.id.spinnerUserSelect); // For selecting user type
        Button registerButton = findViewById(R.id.btnRegister); // For Going to registration page for attendee/organizer
        Button loginButton = findViewById(R.id.btnLogin); // For attempting to process login

        // Set up spinner to choose from Administrator, Organizer, and Attendee
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Reference to the Firebase DB
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // When the user presses the registerButton
        registerButton.setOnClickListener(v -> {
            // Intent for changing to a register screen
            Intent registerIntent;
            // Error message
            Toast error;

            switch (userType) {
                case ATTENDEE:
                    registerIntent = new Intent(MainActivity.this, AttendeeRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case ORGANIZER:
                    registerIntent = new Intent(MainActivity.this, OrganizerRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case ADMINISTRATOR:
                    error = Toast.makeText(MainActivity.this, "No registration needed. Proceed to login.", Toast.LENGTH_SHORT);
                    error.show();
                    break;
                default: // If the user has not selected a user type yet
                    error = Toast.makeText(MainActivity.this, "Please select a user type.", Toast.LENGTH_SHORT);
                    error.show();
            }
        });

        // When the user presses the loginButton
        loginButton.setOnClickListener(v -> {
            // Path for database
            String pathStr;
            // Intent for changing to a welcome screen
            Intent loginIntent;
            // Error message
            Toast error;

            // Determine user type
            switch (userType) {
                case ATTENDEE:
                    loginIntent = new Intent(MainActivity.this, AttendeeWelcomeActivity.class);
                    pathStr = "attendees";
                    break;
                case ORGANIZER:
                    loginIntent = new Intent(MainActivity.this, OrganizerWelcomeActivity.class);
                    pathStr = "organizers";
                    break;
                case ADMINISTRATOR:
                    loginIntent = new Intent(MainActivity.this, AdministratorWelcomeActivity.class);
                    pathStr = "administrators";
                    break;
                default: // If no user type is selected
                    error = Toast.makeText(MainActivity.this, "Please select a user type.", Toast.LENGTH_SHORT);
                    error.show();
                    // Exit lambda expression or onClick()
                    return;
            }

            // Retrieve user input
            String inEmail = etEmail.getText().toString();
            String inPassword = etPassword.getText().toString();

            // Get the reference to the correct child node
            DatabaseReference usersDataBaseRef = databaseReference.child(pathStr);

            // Add listener for fetching data
            usersDataBaseRef.get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    // For iterating through all child objects
                    Iterator<DataSnapshot> iterator = task.getResult().getChildren().iterator();
                    boolean isValid = false;

                    // While theres more elements left and still haven't found a match
                    while (iterator.hasNext() && !isValid) {
                        User user = iterator.next().getValue(User.class);
                        if (user != null && user.getEmail().equals(inEmail) && user.getPassword().equals(inPassword)) {
                            isValid = true;
                            startActivity(loginIntent);
                        }
                    }

                    // No user found message
                    if (!isValid) {
                        Toast toast = Toast.makeText(this, "User not found, please try again.", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });
        });
    }


    // When the user selects an item on the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Determine selected user type
        String spinnerItemStr = (String) parent.getItemAtPosition(pos);

        // Set userType to selected
        switch (spinnerItemStr) {
            case "Attendee":
                userType = UserType.ATTENDEE;
                break;
            case "Organizer":
                userType = UserType.ORGANIZER;
                break;
            case "Administrator":
                userType = UserType.ADMINISTRATOR;
                break;
            default:
                userType = UserType.INVALID;
        }
    }

    // Unused interface callback
    public void onNothingSelected(AdapterView<?> parent) {
    }

}