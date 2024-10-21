package com.example.eams.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.users.Organizer;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdministratorInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrator_inbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        retrieveRegistrationRequests(); // calls this method to populate the RecyclerViewer with data
    }

    private void retrieveRegistrationRequests() {
        // Get the reference to the correct child node
        // TODO: add attendee case, or generalize to user rather than specific cases for organizer and attendee
        DatabaseReference organizersDatabaseReference = FirebaseDatabase.getInstance().getReference("users/organizers");
        DatabaseReference attendeeDatabaseReference = FirebaseDatabase.getInstance().getReference("users/attendees");

        // create a listener to read the data for each user
        organizersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Organizer> organizers = new ArrayList<>();
                for (DataSnapshot organizerSnapshot : dataSnapshot.getChildren()) {
                    // create a new organizer object from the database, we will display this information in the inbox
                    Organizer organizer = organizerSnapshot.getValue(Organizer.class);
                    if (organizer != null) {
                        organizers.add(organizer);
                        // TODO: only add the organizer content if it is not yet approved,
                        //  maybe will require adding an "approved" field to user in database
                        //  and checking whether it is true or false
                    }
                }
                setUpRecyclerView(organizers);
                // TODO: figure out how to add all of the users, not just the first one
                //  (maybe has to do with clearing the list at some point?)
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors:
                System.err.println("Error: " + databaseError.getMessage());
            }
        });
    }

    // sets up RecyclerView, and also sets the adapter
    private void setUpRecyclerView(List<Organizer> organizers) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        OrganizerAdapter adapter = new OrganizerAdapter(organizers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // static OrganizerAdapter class
    public static class OrganizerAdapter extends RecyclerView.Adapter<OrganizerAdapter.ViewHolder> {
        private List<Organizer> organizers;

        // initializes the adapter with a list or Organizer objects
        public OrganizerAdapter(List<Organizer> organizers) {
            this.organizers = organizers;
        }

        // inflates activity_registration_request.xml and creates a new instance of ViewHolder, which will hold the views for this item
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.activity_registration_request, parent, false);
            return new ViewHolder(view);
        }

        // displays data at a specified position, binding Organizer attributes to the TextViews defined in activity_registration_request.xml
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Organizer organizer = organizers.get(position);
            holder.textViewFirstName.setText(organizer.getFirstName());
            holder.textViewLastName.setText(organizer.getLastName());
            holder.textViewPhoneNumber.setText(organizer.getPhoneNumber());
            holder.textViewEmail.setText(organizer.getEmail());
            holder.textViewStreet.setText(organizer.getStreet());
            holder.textViewCity.setText(organizer.getCity());
            holder.textViewProvince.setText(organizer.getProvince());
            holder.textViewPostalCode.setText(organizer.getPostalCode());
            holder.textViewOrganization.setText(organizer.getOrganization());
        }

        @Override
        public int getItemCount() {
            return organizers.size();
        }

        // static class that updates the textViews for registration requests
        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewFirstName, textViewLastName, textViewPhoneNumber, textViewEmail,
                    textViewStreet, textViewCity, textViewProvince, textViewPostalCode, textViewOrganization;
            public ViewHolder(View itemView) {
                super(itemView);
                textViewFirstName = itemView.findViewById(R.id.firstName);
                textViewLastName = itemView.findViewById(R.id.lastName);
                textViewPhoneNumber = itemView.findViewById(R.id.phoneNumber);
                textViewEmail = itemView.findViewById(R.id.email);
                textViewStreet = itemView.findViewById(R.id.street);
                textViewCity = itemView.findViewById(R.id.city);
                textViewProvince = itemView.findViewById(R.id.province);
                textViewPostalCode = itemView.findViewById(R.id.postalCode);
                textViewOrganization = itemView.findViewById(R.id.organization);
            }
        }
    }
}

