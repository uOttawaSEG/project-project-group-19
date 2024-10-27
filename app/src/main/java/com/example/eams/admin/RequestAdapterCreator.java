package com.example.eams.admin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.users.Attendee;
import com.example.eams.users.RegisterUser;
import com.example.eams.users.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public interface RequestAdapterCreator<T extends RegisterUser, VH extends RequestViewHolder> extends LifecycleOwner {
    default RecyclerView.Adapter<VH> getRequestAdapter(
            DatabaseReference userTypeRef,
            String requestType,
            int layout
    ) {
        DatabaseReference pendingUserRef = userTypeRef.child(requestType);

        return new FirebaseRecyclerAdapter<T, VH>(getFirebaseRecyclerOptions(userTypeRef)) {

            @NonNull
            @Override
            public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return initViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(layout, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull T model) {
                DatabaseReference currentUserRef = getRef(position);

                holder.setAcceptOnClickListener(v -> {
                    userTypeRef.child("approved")
                            .push()
                            .setValue(model, (error, ref) -> {
                                if (error != null) {
                                    Log.e("firebase", "Failed to approve user");
                                    return;
                                }

                                currentUserRef.removeValue();
                            });
                });

                if (holder instanceof RejectableRequest) {
                    ((RejectableRequest) holder).setRejectOnClickListener(v -> {
                        userTypeRef.child("rejected")
                                .push()
                                .setValue(model, (error, ref) -> {
                                    if (error != null) {
                                        Log.e("firebase", "Failed to reject user");
                                    }

                                    currentUserRef.removeValue();
                                });
                    });
                }

                holder.bind(model);
            }
        };
    }

    FirebaseRecyclerOptions<T> getFirebaseRecyclerOptions(DatabaseReference userTypeRef);

    VH initViewHolder(View view);
}
