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

import com.example.eams.admin.AdministratorWelcomeActivity;
import com.example.eams.attendee.AttendeeRegisterActivity;
import com.example.eams.attendee.AttendeeWelcomeActivity;
import com.example.eams.organizer.OrganizerRegisterActivity;
import com.example.eams.organizer.OrganizerWelcomeActivity;
import com.example.eams.users.Admin;
import com.example.eams.users.Attendee;
import com.example.eams.users.Organizer;
import com.example.eams.users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Iterator;

/**
 * AttendeeRegisterActivity allows a User to register as an Attendee
 * Bi-directional connection to AttendeeRegisterActivity, AttendeeWelcomeActivity,
 * OrganizerRegisterActivity, OrganizerWelcomeActivity, and AdministratorWelcomeActivity
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // INSTANCE VARIABLES
    // Possible user types
    private enum UserType {
        INVALID,
        ATTENDEE,
        ORGANIZER,
        ADMINISTRATOR
    }

    // Selected user type
    private UserType userType;
    private static final String TAG = "MainActivity";

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
        EditText etEmail = findViewById(R.id.et_main_email);
        EditText etPassword = findViewById(R.id.et_main_password);
        Spinner spinner = findViewById(R.id.spinner_main_user_select); // For selecting user type
        Button registerButton = findViewById(R.id.btn_main_register); // For Going to registration page for attendee/organizer
        Button loginButton = findViewById(R.id.btn_main_login); // For attempting to process login

        // Set up spinner to choose from Administrator, Organizer, and Attendee
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // When the user presses the loginButton
        loginButton.setOnClickListener(v -> {

            Intent i = new Intent(this, AdministratorWelcomeActivity.class);
            startActivity(i);
            return;

//
//            // Path for database
//            String pathStr;
//            // Intent for changing to a welcome screen
//            Intent loginIntent;
//            // Error message
//            Toast error;
//
//            // Determine user type
//            switch (userType) {
//                case ATTENDEE:
//                    loginIntent = new Intent(this, AttendeeWelcomeActivity.class);
//                    pathStr = "users/attendees";
//                    break;
//                case ORGANIZER:
//                    loginIntent = new Intent(this, OrganizerWelcomeActivity.class);
//                    pathStr = "users/organizers";
//                    break;
//                case ADMINISTRATOR:
//                    loginIntent = new Intent(this, AdministratorWelcomeActivity.class);
//                    pathStr = "users/administrators";
//                    break;
//                default: // If no user type is selected
//                    error = Toast.makeText(this, "Please select a user type.", Toast.LENGTH_SHORT);
//                    error.show();
//                    // Exit lambda expression or onClick()
//                    return;
//            }
//
//            // Retrieve user input
//            String inEmail = etEmail.getText().toString().trim();
//            String inPassword = etPassword.getText().toString().trim();
//
//            // Get the reference to the correct child node
//            DatabaseReference usersDatabaseReference = FirebaseDatabase.getInstance().getReference(pathStr);
//
//            // Add listener for fetching data
//            usersDatabaseReference.get().addOnCompleteListener(task -> {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                } else {
//                    // For iterating through all child objects
//                    Iterator<DataSnapshot> iterator = task.getResult().getChildren().iterator();
//                    boolean isValid = false;
//
//                    // While theres more elements left and still haven't found a match
//                    while (iterator.hasNext() && !isValid) {
//                        // Fetched user
//                        User user = null;
//
//                        // Get object of correct type
//                        switch (userType) {
//                            case ATTENDEE:
//                                user = iterator.next().getValue(Attendee.class);
//                                break;
//                            case ORGANIZER:
//                                user = iterator.next().getValue(Organizer.class);
//                                break;
//                            case ADMINISTRATOR:
//                                user = iterator.next().getValue(Admin.class);
//                        }
//
//                        // If user found
//                        if (user != null && user.getEmail().equals(inEmail) && user.getPassword().equals(inPassword)) {
//                            isValid = true;
//                            startActivity(loginIntent);
//                        }
//                    }
//
//                    // No user found message
//                    if (!isValid) {
//                        Toast toast = Toast.makeText(this, "User not found, please try again.", Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//
//                }
//            });
        });

        // When the user presses the registerButton
        registerButton.setOnClickListener(v -> {
            // Intent for changing to a register screen
            Intent registerIntent;
            // Error message
            Toast error;

            switch (userType) {
                case ATTENDEE:
                    registerIntent = new Intent(this, AttendeeRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case ORGANIZER:
                    registerIntent = new Intent(this, OrganizerRegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case ADMINISTRATOR:
                    error = Toast.makeText(this, "No registration needed. Proceed to login.", Toast.LENGTH_SHORT);
                    error.show();
                    break;
                default: // If the user has not selected a user type yet
                    error = Toast.makeText(this, "Please select a user type.", Toast.LENGTH_SHORT);
                    error.show();
            }
        });
    }

    /**
     * Determines selected user type (Attendee, Organizer, or Administrator).
     * Runs when a user selects an item with the spinner.
     *
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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