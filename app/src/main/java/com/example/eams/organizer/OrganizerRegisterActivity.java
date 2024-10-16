package com.example.eams.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.example.eams.MainActivity;
import com.example.eams.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;

/**
 * OrganizerRegisterActivity allows a User to register as an Organizer
 * Bi-directional connection to MainActivity (home/login page)
 *
 * @author Alex Ajersch
 * @author Brooklyn Mcclelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class OrganizerRegisterActivity extends AppCompatActivity{

    // INSTANCE VARIABLES
    private EditText userFirstName, userLastName, userPhoneNumber, etEmail,
            userPassword, userStreetName, userCity, userProvince, userPostalCode;

    private Button confirm_button;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "OrganizerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organiser_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve inputs from Register page
        userFirstName = findViewById(R.id.userFirstName);
        userLastName = findViewById(R.id.userLastName);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        userPassword = findViewById(R.id.userPassword);
        userStreetName = findViewById(R.id.userStreetName);
        userCity = findViewById(R.id.userCity);
        userProvince = findViewById(R.id.userProvince);
        userPostalCode = findViewById(R.id.userPostalCode);
        confirm_button = findViewById(R.id.confirm_button);

        // Confirm button from registration page brings Organizer back to login page (MainActivity)

        View confirmButtonOrganizer = findViewById(R.id.confirm_button);

        confirmButtonOrganizer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = userPassword.getText().toString();

                System.out.println("Email: " + email);
                System.out.println("Password: " + password);

                if (email.isEmpty()) {
                    etEmail.setError("Please enter email");
                    etEmail.requestFocus();
                } else if (password.isEmpty()) {
                    userPassword.setError("Please enter password");
                    userPassword.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    etEmail.setError("Please enter email");
                    etEmail.requestFocus();
                    userPassword.setError("Please enter password");
                    userPassword.requestFocus();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    mAuth.createUserWithEmailAndPassword(email , password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("First Name").setValue(userFirstName.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Last Name").setValue(userLastName.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Phone Number").setValue(userPhoneNumber.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(etEmail.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Street Name").setValue(userStreetName.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("City").setValue(userCity.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Province").setValue(userProvince.getText().toString());
                                        mDatabase.child("Users").child("Organizers").child(mAuth.getCurrentUser().getUid()).child("Postal Code").setValue(userPostalCode.getText().toString());

                                        Intent intent = new Intent(OrganizerRegisterActivity.this, MainActivity.class);
                                        startActivity(intent);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());


                                    }
                                }
                            });




                    /*
                    user.sendEmailVerification().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OrganizerRegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                     */
                }

                /*Intent intent = new Intent(OrganizerRegisterActivity.this, MainActivity.class);
                startActivity(intent);*/
            }
        });
    }

}