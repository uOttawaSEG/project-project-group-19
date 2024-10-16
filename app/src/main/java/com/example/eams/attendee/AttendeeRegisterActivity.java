package com.example.eams.attendee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.example.eams.users.Attendee;
import com.example.eams.users.Organizer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AttendeeRegisterActivity allows a User to register as an Attendee
 * Bi-directional connection to MainActivity (home/login page)
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AttendeeRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Boilerplate
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendee_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize refs to Views
        EditText etFirstName = findViewById(R.id.et_attendee_register_first_name);
        EditText etLastName = findViewById(R.id.et_attendee_register_last_name);
        EditText etPhoneNumber = findViewById(R.id.et_attendee_register_phone_number);
        EditText etEmail = findViewById(R.id.et_attendee_register_email);
        EditText etPassword = findViewById(R.id.et_attendee_register_password);
        EditText etStreet = findViewById(R.id.et_attendee_register_street);
        EditText etCity = findViewById(R.id.et_attendee_register_city);
        EditText etProvince = findViewById(R.id.et_attendee_register_province);
        EditText etPostalCode = findViewById(R.id.et_attendee_register_postal_code);
        Button btnConfirm = findViewById(R.id.btn_attendee_register_confirm);


        // When the user presses the confirm button
        btnConfirm.setOnClickListener(v -> {
            // Error message
            Toast error;


            // Retrieve user input
            String inFirstName = etFirstName.getText().toString().trim();
            String inLastName = etLastName.getText().toString().trim();
            String inPhoneNumber = etPhoneNumber.getText().toString().trim();
            String inEmail = etEmail.getText().toString().trim();
            String inPassword = etPassword.getText().toString().trim();
            String inStreet = etStreet.getText().toString().trim();
            String inCity = etCity.getText().toString().trim();
            String inProvince = etProvince.getText().toString().trim();
            String inPostalCode = etPostalCode.getText().toString().trim();


            // Validate user input
            boolean isComplete = !inFirstName.isEmpty() && !inLastName.isEmpty() &&
                    !inPhoneNumber.isEmpty() && !inEmail.isEmpty() &&
                    !inPassword.isEmpty() && !inStreet.isEmpty() &&
                    !inCity.isEmpty() && !inProvince.isEmpty() &&
                    !inPostalCode.isEmpty();

            if (!isComplete) {
                error = Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }

            // Create new Organizer instance for field validation
            Attendee attendee = new Attendee(
                    inEmail,
                    inPassword,
                    inFirstName,
                    inLastName,
                    inPhoneNumber,
                    inStreet,
                    inCity,
                    inProvince,
                    inPostalCode
            );

            if (!attendee.firstNameIsValid()) {
                error = Toast.makeText(this, "Invalid first name.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }

            if (!attendee.lastNameIsValid()) {
                error = Toast.makeText(this, "Invalid last name.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }


            if (!attendee.phoneNumberIsValid()){
                error = Toast.makeText(this, "Invalid phone number.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }

            if (!attendee.emailIsValid()) {
                error = Toast.makeText(this, "Invalid email.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }

            if (!attendee.passwordIsValid()) {
                error = Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }

            if (!attendee.addressIsValid()){
                error = Toast.makeText(this, "Invalid address.", Toast.LENGTH_SHORT);
                error.show();
                return;
            }


            // Add new Organizer to the database
            registerAttendee(attendee);

            // Return to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }

    private void registerAttendee(Attendee attendee) {
        // Get the reference to the correct child node
        DatabaseReference organizersDatabaseReference = FirebaseDatabase.getInstance().getReference("users/attendees");

        // Generate unique key for new entry
        String key = organizersDatabaseReference.push().getKey();

        // Add the user to the database
        organizersDatabaseReference.child(key).setValue(attendee);
    }


}