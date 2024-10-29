package com.example.eams.admin.request;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.users.RegisterUser;

/**
 * Abstract representation of a ViewHolder for a registration request
 *
 * @author Alex Ajersch
 * @author Brooklyn McClelland
 * @author Moïse Kenge Ngoyi
 * @author Naomi Braun
 * @author Rachel Qi
 * @author Steven Wu
 */
public abstract class RequestViewHolder extends RecyclerView.ViewHolder  {
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvPhoneNumber;
    private TextView tvEmail;
    private TextView tvStreet;
    private TextView tvCity;
    private TextView tvProvince;
    private TextView tvPostalCode;
    private Button btnAccept;

    /**
     * Constructor for a RequestViewHolder
     * @param itemView the View used to initialize the RequestViewHolder
     */
    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Populates the view with the given user's information
     * @param user the user who's data will be shown
     */
    public void bind(@NonNull RegisterUser user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhoneNumber(user.getPhoneNumber());
        setEmail(user.getEmail());
        setStreet(user.getStreet());
        setCity(user.getCity());
        setProvince(user.getProvince());
        setPostalCode(user.getPostalCode());
    }

    /**
     * Finds the accept button
     * @param id the id of the element
     */
    public void setBtnAccept(int id) {
        this.btnAccept = itemView.findViewById(id);
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
     * Finds the Email TextView
     * @param id the id of the element
     */
    public void setTvEmail(int id) {
        this.tvEmail = itemView.findViewById(id);
    }

    /**
     * Finds the Phone Number TextView
     * @param id the id of the element
     */
    public void setTvPhoneNumber(int id) {
        this.tvPhoneNumber = itemView.findViewById(id);
    }

    /**
     * Finds the LastName TextView
     * @param id the id of the element
     */
    public void setTvLastName(int id) {
        this.tvLastName = itemView.findViewById(id);
    }

    /**
     * Finds the First Name TextView
     * @param id the id of the element
     */
    public void setTvFirstName(int id) {
        this.tvFirstName = itemView.findViewById(id);
    }

    /**
     * Sets the OnClickListener for the Accept Button
     * @param l the OnClickListener to be set
     */
    public void setAcceptOnClickListener(View.OnClickListener l) {
        if (btnAccept == null) {
            return;
        }
        btnAccept.setOnClickListener(l);
    }

    /**
     * Sets the content of the First Name TextView
     * @param firstName the content to be set
     */
    private void setFirstName(String firstName) {
        if (tvFirstName == null) {
            return;
        }
        tvFirstName.setText(firstName);
    }

    /**
     * Sets the content of the Last Name TextView
     * @param lastName the content to be set
     */
    public void setLastName(String lastName) {
        if (tvLastName == null) {
            return;
        }
        tvLastName.setText(lastName);
    }

    /**
     * Sets the content of the Phone Number TextView
     * @param phoneNumber the content to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        if (tvPhoneNumber == null) {
            return;
        }
        tvPhoneNumber.setText(phoneNumber);
    }

    /**
     * Sets the content of the Email TextView
     * @param email the content to be set
     */
    public void setEmail(String email) {
        if (tvEmail == null) {
            return;
        }
        tvEmail.setText(email);
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
