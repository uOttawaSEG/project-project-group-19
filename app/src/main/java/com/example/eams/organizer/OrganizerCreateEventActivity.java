package com.example.eams.organizer;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.example.eams.users.Attendee;
import com.example.eams.users.Event;
import com.example.eams.users.Organizer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * OrganizerCreateEventActivity displays the Organizer user's event creation page,
 * where they can create new events.
 * Bi-directional connection to OrganizerWelcomeActivity
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */

public class OrganizerCreateEventActivity extends AppCompatActivity {

    // ** INSTANCE VARIABLES **************************************************
    private Switch switchWidget;
    private boolean approvalIsAutomatic;

    //  ** INSTANCE METHODS **************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizer_create_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize refs to Views
        EditText etTitle = findViewById(R.id.et_new_event_title);
        EditText etDescription = findViewById(R.id.et_new_event_description);
        Button btnDate = findViewById(R.id.et_new_event_date);
        Button btnStartTime = findViewById(R.id.et_new_event_start_time);
        Button btnEndTime = findViewById(R.id.et_new_event_end_time);
        EditText etStreet = findViewById(R.id.et_new_event_street);
        EditText etCity = findViewById(R.id.et_new_event_city);
        EditText etProvince = findViewById(R.id.et_new_event_province);
        EditText etPostalCode = findViewById(R.id.et_new_event_postal_code);
        Button btnCreateEvent = findViewById(R.id.btn_create_new_event);
        switchWidget = findViewById(R.id.switch_manual_auto_accept);

        // listener for date picker
        btnDate.setOnClickListener(v -> {
            // use the current date as default in the calendar
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // TODO: create and show DatePickerDialog
        });

        // Set approvalIsAutomatic to true if switchWidget is checked, false if not
        approvalIsAutomatic = switchWidget.isChecked();

        // Listener for switchWidget changes between manual and automatic
        switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * Set approvalIsAutomatic to true if switchWidget is checked, false if not
             * @param button
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                approvalIsAutomatic = isChecked;
            }
        });

        // when the user presses CreateEvent
        btnCreateEvent.setOnClickListener(v -> {
            // initialize error message
            Toast error;

            String inTitle = etTitle.getText().toString().trim();
            String inDescription = etDescription.getText().toString().trim();
            String inStreet = etStreet.getText().toString().trim();
            String inCity = etCity.getText().toString().trim();
            String inProvince = etProvince.getText().toString().trim();
            String inPostalCode = etPostalCode.getText().toString().trim();

            // TODO: Change these strings to obtain input from app
            // Temporary Strings so that constructor will work
            String date = "November 19";
            String startTime = "12:00 am";
            String endTime = "11:59 pm";

            // Create new Event instance for field validation
            Event event = new Event(
                    inTitle,
                    inDescription,
                    date,
                    startTime,
                    endTime,
                    inStreet,
                    inCity,
                    inProvince,
                    inPostalCode,
                    approvalIsAutomatic
            );

            }
        );
    }


}