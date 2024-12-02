package com.example.eams.attendee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.event.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class AttendeeEventViewAdapter extends FirebaseRecyclerAdapter<Event, AttendeeEventViewAdapter.AttendeeEventViewHolder> {

    private String userDatabaseKey;


    /**
     * Constructor for an AttendeeEventViewAdapter
     * @param options
     * @param userDatabaseKey
     */
    public AttendeeEventViewAdapter(@NonNull FirebaseRecyclerOptions<Event> options, String userDatabaseKey) {
        super(options);
        this.userDatabaseKey = userDatabaseKey;
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendeeEventViewHolder holder, int position, @NonNull Event model) {

        DatabaseReference eventsRegisteredToRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("users/attendees/approved/" + userDatabaseKey + "/eventsRegisteredTo");

        eventsRegisteredToRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, String> eventsRegisteredTo = (Map<String, String>) dataSnapshot.getValue();
                if (eventsRegisteredTo != null && eventsRegisteredTo.containsKey(model.getDatabaseKey())) {
                    holder.bind(model);
                    String approvalStatus = eventsRegisteredTo.get(model.getDatabaseKey());
                    holder.setApprovalStatus(approvalStatus);
                } else {
                    holder.itemView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Cancel Event Registration if event status is pending and event is in more than 24 hours
        holder.cancelEventRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventDatabaseKey = model.getDatabaseKey();
                String eventDate = model.getDate();
                String eventTime = model.getStartTime();

                // Combine date time for comparison
                String combinedDateTime = eventDate + " " + eventTime;

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

                try{
                    Date eventDateTime = dateFormat.parse(combinedDateTime);
                    Date currentDate = new Date();

                    long timeDiffInMillis = eventDateTime.getTime() - currentDate.getTime();
                    int timeDiffInHours = (int) timeDiffInMillis / (60 * 60 * 1000);

                    if(timeDiffInHours > 24){
                        // Delete the event from the database
                        int position = holder.getBindingAdapterPosition();
                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference eventRefFromUser = databaseRef.child("users/attendees/approved/" + userDatabaseKey + "/eventsRegisteredTo/" + eventDatabaseKey);
                        eventRefFromUser.removeValue();

                        DatabaseReference eventRefFromEvent = databaseRef.child("events/" + eventDatabaseKey + "/registeredAttendees/" + userDatabaseKey);
                        eventRefFromEvent.removeValue();

                        // Remove from RecyclerView
                        getSnapshots().remove(position);
                        notifyItemRemoved(position);
                    } else{
                        Toast.makeText(v.getContext(), "Event registration cannot be cancelled within 24 hours of the event.", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    Toast.makeText(v.getContext(), "Error parsing date and time", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @NonNull
    @Override
    public AttendeeEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the new layout file for each RecyclerView item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_attendee_event, parent, false);
        return new AttendeeEventViewHolder(itemView);
    }

    /**
     * ViewHolder for an event from Attendee's POV
     *
     * @author Alex Ajersch
     * @author Brooklyn McClelland
     * @author Moïse Kenge Ngoyi
     * @author Naomi Braun
     * @author Rachel Qi
     * @author Steven Wu
     */
    public class AttendeeEventViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvStreet;
        private TextView tvCity;
        private TextView tvProvince;
        private TextView tvPostalCode;
        private ImageView tvApprovalIndicator;
        private TextView tvApprovalText;
        private TextView cancelEventRegistration;

        /**
         * Constructor for a EventViewHolder
         * @param itemView the View used to initialize the EventViewHolder
         */
        public AttendeeEventViewHolder(@NonNull View itemView) {
            super(itemView);
            setTvTitle(R.id.event_search_title);
            setTvDescription(R.id.event_search_description);
            setTvDate(R.id.event_date);
            setTvStartTime(R.id.event_start_time);
            setTvEndTime(R.id.event_end_time);
            setTvStreet(R.id.event_street);
            setTvCity(R.id.event_city);
            setTvProvince(R.id.event_province);
            setTvPostalCode(R.id.event_postal_code);
            setApprovalIndicator(R.id.approval_indicator);
            setApprovalText(R.id.approval_text);
            setTvCancelEventRegistration(R.id.cancel_event_registration);
        }

        /**
         * Populates the view with the given user's information
         * @param event the event who's data will be shown
         */
        public void bind(@NonNull Event event) {
            setTitle(event.getTitle());
            setDescription(event.getDescription());
            setDate(event.getDate());
            setStartTime(event.getStartTime());
            setEndTime(event.getEndTime());
            setStreet(event.getStreet());
            setCity(event.getCity());
            setProvince(event.getProvince());
            setPostalCode(event.getPostalCode());
        }

        /**
         * Finds the cancel button
         * @param id the id of the element
         */
        public void setTvCancelEventRegistration(int id) {
            this.cancelEventRegistration = itemView.findViewById(id);
        }
        /**
         * Finds the Postal Code TextView
         * @param id the id of the element
         */
        public void setTvPostalCode(int id) {
            this.tvPostalCode = itemView.findViewById(id);
        }

        /**
         * Finds the Province TextView
         * @param id the id of the element
         */
        public void setTvProvince(int id) {
            this.tvProvince = itemView.findViewById(id);
        }

        /**
         * Finds the City TextView
         * @param id the id of the element
         */
        public void setTvCity(int id) {
            this.tvCity = itemView.findViewById(id);
        }

        /**
         * Finds the Street TextView
         * @param id the id of the element
         */
        public void setTvStreet(int id) {
            this.tvStreet = itemView.findViewById(id);
        }

        /**
         * Finds the Date TextView
         * @param id the id of the element
         */
        public void setTvDate(int id) {
            this.tvDate = itemView.findViewById(id);
        }

        /**
         * Finds the StartTime TextView
         * @param id the id of the element
         */
        public void setTvStartTime(int id) {
            this.tvStartTime = itemView.findViewById(id);
        }

        /**
         * Finds the EndTime TextView
         * @param id the id of the element
         */
        public void setTvEndTime(int id) {
            this.tvEndTime = itemView.findViewById(id);
        }

        /**
         * Finds the Description TextView
         * @param id the id of the element
         */
        public void setTvDescription(int id) {
            this.tvDescription = itemView.findViewById(id);
        }

        /**
         * Finds the Title TextView
         * @param id the id of the element
         */
        public void setTvTitle(int id) {
            this.tvTitle = itemView.findViewById(id);
        }

        /**
         * Sets the OnClickListener for the Accept Button
         * @param l the OnClickListener to be set
         */
        public void setCancelRegistrationOnClickListener(View.OnClickListener l) {
            if (cancelEventRegistration == null) {
                return;
            }
            cancelEventRegistration.setOnClickListener(l);
        }

        /**
         * Sets the content of the First Name TextView
         * @param title the content to be set
         */
        private void setTitle(String title) {
            if (tvTitle == null) {
                return;
            }
            tvTitle.setText(title);
        }

        /**
         * Sets the content of the Last Name TextView
         * @param description the content to be set
         */
        public void setDescription(String description) {
            if (tvDescription == null) {
                return;
            }
            tvDescription.setText(description);
        }

        /**
         * Sets the content of the Email TextView
         * @param date the content to be set
         */
        public void setDate(String date) {
            if (tvDate == null) {
                return;
            }
            tvDate.setText(date);
        }

        /**
         * Sets the content of the Phone Number TextView
         * @param startTime the content to be set
         */
        public void setStartTime(String startTime) {
            if (tvStartTime == null) {
                return;
            }
            tvStartTime.setText(startTime);
        }

        /**
         * Sets the content of the Email TextView
         * @param endTime the content to be set
         */
        public void setEndTime(String endTime) {
            if (tvEndTime == null) {
                return;
            }
            tvEndTime.setText(endTime);
        }

        /**
         * Sets the content of the Street TextView
         * @param street the content to be set
         */
        public void setStreet(String street) {
            if (tvStreet == null) {
                return;
            }
            tvStreet.setText(street);
        }

        /**
         * Sets the content of the City TextView
         * @param city the content to be set
         */
        public void setCity(String city) {
            if (tvCity == null) {
                return;
            }
            tvCity.setText(city);
        }

        /**
         * Sets the content of the Province TextView
         * @param province the content to be set
         */
        public void setProvince(String province) {
            if (tvProvince == null) {
                return;
            }
            tvProvince.setText(province);
        }

        /**
         * Sets the content of the Postal Code TextView
         * @param postalCode the content to be set
         */
        public void setPostalCode(String postalCode) {
            if (tvPostalCode == null) {
                return;
            }
            tvPostalCode.setText(postalCode);
        }

        public void setApprovalIndicator(int id) {
            this.tvApprovalIndicator = itemView.findViewById(id);
        }

        public void setApprovalText(int id) {
            this.tvApprovalText = itemView.findViewById(id);
        }

        public void setApprovalStatus(String status) {

            switch (status) {
                case "approved":
                    tvApprovalIndicator.setImageResource(R.drawable.approved);
                    tvApprovalText.setText("Approved");
                    cancelEventRegistration.setVisibility(View.GONE);
                    break;
                case "rejected":
                    tvApprovalIndicator.setImageResource(R.drawable.rejected);
                    tvApprovalText.setText("Rejected");
                    cancelEventRegistration.setVisibility(View.GONE);
                    break;
                default:
                    tvApprovalIndicator.setImageResource(R.drawable.pending);
                    tvApprovalText.setText("Pending");
                    break;
            }
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getTvStartTime() {
            return tvStartTime;
        }

        public TextView getTvEndTime() {
            return tvEndTime;
        }

        public TextView getTvStreet() {
            return tvStreet;
        }

        public TextView getTvCity() {
            return tvCity;
        }

        public TextView getTvProvince() {
            return tvProvince;
        }

        public TextView getTvPostalCode() {
            return tvPostalCode;
        }

        public TextView getCancelEventRegistration() {
            return cancelEventRegistration;
        }

        public ImageView getTvApprovalIndicator() {
            return tvApprovalIndicator;
        }

        public TextView getTvApprovalText() {
            return tvApprovalText;
        }
    }
}
