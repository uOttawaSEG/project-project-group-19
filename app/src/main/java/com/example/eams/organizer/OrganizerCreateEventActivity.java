package com.example.eams.organizer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.R;
import com.example.eams.event.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

        // Boilerplate
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
        Spinner spStartTimeHour = findViewById(R.id.spinner_start_time_hour); // For selecting start time hour
        Spinner spStartTimeMinute = findViewById(R.id.spinner_start_time_minute); // For selecting start time minute
        Spinner spEndTimeHour = findViewById(R.id.spinner_end_time_hour); // For selecting end time hour
        Spinner spEndTimeMinute = findViewById(R.id.spinner_end_time_minute); // For selecting end time minute
        EditText etStreet = findViewById(R.id.et_new_event_street);
        EditText etCity = findViewById(R.id.et_new_event_city);
        EditText etProvince = findViewById(R.id.et_new_event_province);
        EditText etPostalCode = findViewById(R.id.et_new_event_postal_code);
        Button btnCreateEvent = findViewById(R.id.btn_create_new_event);
        switchWidget = findViewById(R.id.switch_manual_auto_accept);

        // listener for date picker with restriction on past dates
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dateDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, month);
                    selectedDate.set(Calendar.DAY_OF_MONTH, day);

                    // Ensure selected date is not in the past
                    if (selectedDate.before(c)) {
                        Toast.makeText(OrganizerCreateEventActivity.this, "Please choose a future date.", Toast.LENGTH_SHORT).show();
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
                        btnDate.setText(dateFormat.format(selectedDate.getTime()));
                    }
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dateDialog.show();
        });

        // set time based on spinner
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.hour_spinner_items, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> minuteAdapter = ArrayAdapter.createFromResource(this, R.array.minute_spinner_items, android.R.layout.simple_spinner_item);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spStartTimeHour.setAdapter(hourAdapter);
        spStartTimeMinute.setAdapter(minuteAdapter);
        spEndTimeHour.setAdapter(hourAdapter);
        spEndTimeMinute.setAdapter(minuteAdapter);

        // Set approvalIsAutomatic to true if switchWidget is checked, false if not
        approvalIsAutomatic = switchWidget.isChecked();
        switchWidget.setOnCheckedChangeListener((button, isChecked) -> approvalIsAutomatic = isChecked);

        // Create Event button click listener with validation
        btnCreateEvent.setOnClickListener(v -> {
            String inTitle = etTitle.getText().toString().trim();
            String inDescription = etDescription.getText().toString().trim();
            String inStreet = etStreet.getText().toString().trim();
            String inCity = etCity.getText().toString().trim();
            String inProvince = etProvince.getText().toString().trim();
            String inPostalCode = etPostalCode.getText().toString().trim();

            // Obtain and parse date from btnDate
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault());
            LocalDate day = null;
            try {
                day = LocalDate.parse(btnDate.getText().toString(), dateFormatter);
            } catch (DateTimeParseException e) {
                Toast.makeText(this, "Please select a valid date.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtain and parse time from spinner selection
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm", Locale.getDefault());
            LocalTime startTime, endTime;

            // Format time strings to ensure two digits for hour and minute
            String startTimeHour = String.format("%02d", Integer.parseInt(spStartTimeHour.getSelectedItem().toString()));
            String endTimeHour = String.format("%02d", Integer.parseInt(spEndTimeHour.getSelectedItem().toString()));
            String startTimeMinute = spStartTimeMinute.getSelectedItem().toString();
            String endTimeMinute = spEndTimeMinute.getSelectedItem().toString();

            try {
                startTime = LocalTime.parse(startTimeHour + startTimeMinute, timeFormatter);
                endTime = LocalTime.parse(endTimeHour + endTimeMinute, timeFormatter);

                // Ensure end time is after start time
                if (endTime.isBefore(startTime)) {
                    Toast.makeText(this, "End time must be after start time.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (DateTimeParseException e) {
                Toast.makeText(this, "Please select valid start and end times.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new Event instance
            Event event = new Event(
                    inTitle,
                    inDescription,
                    day.toString(),
                    startTime.toString(),
                    endTime.toString(),
                    inStreet,
                    inCity,
                    inProvince,
                    inPostalCode,
                    approvalIsAutomatic
            );

            if (!event.titleIsValid()) {
                Toast error = Toast.makeText(this, "Invalid title", Toast.LENGTH_LONG);
                error.show();
                return;
            }

            if (!event.descriptionIsValid()) {
                Toast error = Toast.makeText(this, "Invalid description", Toast.LENGTH_LONG);
                error.show();
                return;
            }

            if (!event.addressIsValid()) {
                Toast error = Toast.makeText(this, "Invalid address", Toast.LENGTH_LONG);
                error.show();
                return;
            }

            // Add new event to the database
            writeEvent(event);

            // Display event details in TextView for verification
            TextView tvEventDetails = findViewById(R.id.tv_event_details);
            tvEventDetails.setText(
                    event.getTitle() + event.getDescription() + event.getDate() + event.getStartTime() +
                            event.getEndTime() + event.getStreet() + event.getCity() + event.getProvince() +
                            event.getPostalCode() + event.approvalIsAutomatic()
            );
        });
    }

    /**
     * Writes an Event to database with success and failure messages
     *
     * @param event event to be written to the database
     */
    private void writeEvent(Event event) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventsReference = databaseReference.child("events");
        String eventKey = eventsReference.push().getKey();
        event.setDatabaseKey(eventKey);

        // Add the event to the database with success and failure toast messages
        assert eventKey != null;
        eventsReference.child(eventKey).setValue(event)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to create event.", Toast.LENGTH_SHORT).show();
                });

        // TODO: Remove in deliverable 4
        // Add 2 pending event registrations to begin with

        // Linkage from event
        DatabaseReference eventReference = eventsReference.child(eventKey);

        // Linkage from attendee
        DatabaseReference approvedAttendeesRef = databaseReference.child("users/attendees/approved");

        // In Deliverable 4, an Attendee will check if Event approvalIsAutomatic when they sign up to the Event.
        // If the Event approvalIsAutomatic, the attendee's eventsRegisteredTo value will be set to "approved" immediately

        // Update Firebase
        if (event.approvalIsAutomatic()) {
            eventReference.child("registeredAttendees/a1").setValue("approved");
            eventReference.child("registeredAttendees/a2").setValue("approved");
            approvedAttendeesRef.child("a1/eventsRegisteredTo/" + eventKey).setValue("approved");
            approvedAttendeesRef.child("a2/eventsRegisteredTo/" + eventKey).setValue("approved");
        } else {
            eventReference.child("registeredAttendees/a1").setValue("pending");
            eventReference.child("registeredAttendees/a2").setValue("pending");
            approvedAttendeesRef.child("a1/eventsRegisteredTo/" + eventKey).setValue("pending");
            approvedAttendeesRef.child("a2/eventsRegisteredTo/" + eventKey).setValue("pending");
        }

    }
}