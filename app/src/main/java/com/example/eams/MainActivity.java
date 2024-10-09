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

import com.example.eams.admin.AdminLoginActivity;
import com.example.eams.attendee.AttendeeLoginActivity;
import com.example.eams.organizer.OrganizerLoginActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//    Button attendeeLoginButton;
//    Button organizerLoginButton;
//    Button adminLoginButton;
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

        // Set up dropdown menu to choose from Administrator, Organizer, and Attendee
        Spinner spinner = (Spinner) findViewById(R.id.userSelectSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        /* Find all the buttons by their IDs */
        //attendeeLoginButton = findViewById(R.id.button_attendee_login);
        //organizerLoginButton = findViewById(R.id.button_organizer_login);
        //adminLoginButton = findViewById(R.id.button_admin_login);

        /*
        Links the buttons to their corresponding activities
        attendeeLoginButton  -> AttendeeLoginActivity
        organizerLoginButton -> OrganizerLoginActivity
        adminLoginButton     -> AdminLoginActivity
         */
//        attendeeLoginButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, AttendeeLoginActivity.class);
//            startActivity(intent);
//        });
//
//        organizerLoginButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, OrganizerLoginActivity.class);
//            startActivity(intent);
//        });
//
//        adminLoginButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
//            startActivity(intent);
//        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        String selectedUserType = (String) parent.getItemAtPosition(pos);
        if(selectedUserType.equals("Administrator")){
            CharSequence text = "I'm an Administrator!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else if(selectedUserType.equals("Organizer")){
            CharSequence text = "I'm an Organizer!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else if(selectedUserType.equals("Attendee")){
            CharSequence text = "I'm an Attendee!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else{
            CharSequence text = "Must select a user type!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    public void onNothingSelected(AdapterView<?> parent){
        return;
    }
}