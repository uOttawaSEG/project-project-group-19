package com.example.eams.admin;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.users.RegisterUser;

import java.util.function.Supplier;

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

    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Populates the view with the given attendee's information
     * @param user the attendee who's data will be shown
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
    
    public void setBtnAccept(int id) {
        this.btnAccept = itemView.findViewById(id);
    }

    public void setTvPostalCode(int id) {
        this.tvPostalCode = itemView.findViewById(id);
    }

    public void setTvProvince(int id) {
        this.tvProvince = itemView.findViewById(id);
    }

    public void setTvCity(int id) {
        this.tvCity = itemView.findViewById(id);
    }

    public void setTvStreet(int id) {
        this.tvStreet = itemView.findViewById(id);
    }

    public void setTvEmail(int id) {
        this.tvEmail = itemView.findViewById(id);
    }

    public void setTvPhoneNumber(int id) {
        this.tvPhoneNumber = itemView.findViewById(id);
    }

    public void setTvLastName(int id) {
        this.tvLastName = itemView.findViewById(id);
    }

    public void setTvFirstName(int id) {
        this.tvFirstName = itemView.findViewById(id);
    }

    public void setAcceptOnClickListener(View.OnClickListener l) {
        if (btnAccept == null) {
            return;
        }
        btnAccept.setOnClickListener(l);
    }

    private void setFirstName(String firstName) {
        if (tvFirstName == null) {
            return;
        }
        tvFirstName.setText(firstName);
    }

    public void setLastName(String lastName) {
        if (tvLastName == null) {
            return;
        }
        tvLastName.setText(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        if (tvPhoneNumber == null) {
            return;
        }
        tvPhoneNumber.setText(phoneNumber);
    }

    public void setEmail(String email) {
        if (tvEmail == null) {
            return;
        }
        tvEmail.setText(email);
    }

    public void setStreet(String street) {
        if (tvStreet == null) {
            return;
        }
        tvStreet.setText(street);
    }

    public void setCity(String city) {
        if (tvCity == null) {
            return;
        }
        tvCity.setText(city);
    }

    public void setProvince(String province) {
        if (tvProvince == null) {
            return;
        }
        tvProvince.setText(province);
    }

    public void setPostalCode(String postalCode) {
        if (tvPostalCode == null) {
            return;
        }
        tvPostalCode.setText(postalCode);
    }
}
