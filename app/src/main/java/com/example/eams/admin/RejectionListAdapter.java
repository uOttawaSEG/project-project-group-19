package com.example.eams.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eams.R;
import com.example.eams.users.RegisterUser;

import java.util.ArrayList;
import java.util.List;

public class RejectionListAdapter extends ArrayAdapter<RegisterUser> {

    public RejectionListAdapter(Context context, ArrayList<RegisterUser> rejectedUserList){
        super(context, R.layout.rejected_list_item, rejectedUserList);
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.rejected_list_item, parent, false);
        }

        RegisterUser user = getItem(pos);
        TextView userFullName = view.findViewById(R.id.tv_user_full_name);
        TextView userType = view.findViewById(R.id.tv_user_type);

        userFullName.setText(user.getFullName());
        userType.setText(user.getClass().getSimpleName());

        return view;
    }
}
