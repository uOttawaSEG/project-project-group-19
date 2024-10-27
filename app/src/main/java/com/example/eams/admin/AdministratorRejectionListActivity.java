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
import com.example.eams.databinding.ActivityAdministratorRejectedListBinding;
import com.example.eams.databinding.ActivityMainBinding;
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
public class AdministratorRejectionListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        ActivityAdministratorRejectedListBinding binding = ActivityAdministratorRejectedListBinding.inflate(getLayoutInflater());
        ArrayList<RegisterUser> rejectedUserList = new ArrayList<RegisterUser>();
        ListView rejectedListView = (ListView) findViewById(R.id.lv_rejected_list);
        RejectionListAdapter listAdapter = new RejectionListAdapter(this,rejectedUserList);

        binding.lvRejectedList.setAdapter(listAdapter);
        binding.lvRejectedList.setClickable(true);

        binding.lvRejectedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                RegisterUser clickedUser = (RegisterUser) binding.lvRejectedList.getItemAtPosition(i);

                Intent intent = new Intent(AdministratorRejectionListActivity.this, AdministratorRegistrationResponseActivity.class);
                intent.putExtra("user",clickedUser);
            }
        });

    }
}
