package com.example.eams.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eams.MainActivity;
import com.example.eams.R;
import com.example.eams.attendee.AttendeeWelcomeActivity;
import com.example.eams.users.RegisterUser;

import java.util.ArrayList;

/**
 * AdministratorRejectedListActivity lists the registrations that the Administrator
 * previous rejected.
 * The Administrator can select any request from this list and approve the registration.
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class AdministratorRejectedListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrator_rejected_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<RegisterUser> rejectedUserList = new ArrayList<RegisterUser>();

        ListView rejectedList = (ListView) findViewById(R.id.lv_rejected_list);
        rejectedList.setOnItemClickListener(this);

        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendeeWelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

        RegisterUser user = rejectedList.getItemAtPosition(pos);
        Intent intent = new Intent(AdministratorRejectedListActivity.this, AdministratorRegistrationResponseActivity.class);
        intent.putExtra("position", pos);
    }
}
