package com.example.eams.event;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;

/**
 * Abstract representation of a ViewHolder for an event
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvStreet;
    private TextView tvCity;
    private TextView tvProvince;
    private TextView tvPostalCode;
    private Button btnViewRegistrations;

    /**
     * Constructor for a EventViewHolder
     * @param itemView the View used to initialize the EventViewHolder
     */
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        setTvTitle(R.id.event_title);
        setTvDescription(R.id.event_description);
        setTvDate(R.id.event_date);
        setTvStartTime(R.id.event_start_time);
        setTvEndTime(R.id.event_end_time);
        setTvStreet(R.id.event_street);
        setTvCity(R.id.event_city);
        setTvProvince(R.id.event_province);
        setTvPostalCode(R.id.event_postal_code);
        setBtnViewRegistrations(R.id.btn_event_view_registrations);
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
     * Finds the accept button
     * @param id the id of the element
     */
    public void setBtnViewRegistrations(int id) {
        this.btnViewRegistrations = itemView.findViewById(id);
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
    public void setViewEventsOnClickListener(View.OnClickListener l) {
        if (btnViewRegistrations == null) {
            return;
        }
        btnViewRegistrations.setOnClickListener(l);
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
}


