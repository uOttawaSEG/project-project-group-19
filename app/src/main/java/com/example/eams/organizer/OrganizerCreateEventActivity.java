package com.example.eams.organizer;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            Calendar c = Calendar.getInstance();
            DatePickerDialog dateDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                // gets date that user has selected
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DAY_OF_MONTH, day);
                    // Format the date in words
                    SimpleDateFormat formattedDate = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
                    btnDate.setText(formattedDate.format(c.getTime()));
                    //TODO: make sure past date is not selected

                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)); // this is the initial date that the calendar opens with
            dateDialog.show();
        });

        // listener for start time picker
        btnStartTime.setOnClickListener(v -> {
            showTimePickerDialog(btnStartTime);
        });

        // listener for end time picker
        btnEndTime.setOnClickListener(v -> {
            showTimePickerDialog(btnEndTime);
        });

        // TODO: need to make sure start time is before the end time

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
            String date = btnDate.getText().toString().trim(); // this should correctly obtain app input
            String startTime = btnStartTime.getText().toString().trim();
            String endTime = btnEndTime.getText().toString().trim();

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
    // displays selected time
    private void showTimePickerDialog(Button targetButton) {
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                targetButton.setText(hourOfDay + ":" + minute);
            }
        }, 12, 0, true); // initial time is 12pm
        dialog.show();
    }


}