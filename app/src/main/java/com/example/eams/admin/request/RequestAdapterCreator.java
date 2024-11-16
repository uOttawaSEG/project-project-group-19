package com.example.eams.admin.request;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.users.RegisterUser;
import com.example.eams.external.GMail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for fragments to implement so that they can create a RecyclerView.Adapter for
 * their corresponding RecyclerView
 *
 * @param <T>  the type of the user (Attendee, or Organizer)
 * @param <VH> the type of ViewHolder
 */
interface RequestAdapterCreator<T extends RegisterUser, VH extends RequestViewHolder> extends LifecycleOwner {
    /**
     * Gets a FirebaseRecyclerAdapter to populate a RecyclerView
     * with the data in the given DatabaseReference
     *
     * @param userTypeRef a reference to the node representing the user's type.
     *                    Should be "users/attendees" or "users/organizers"
     * @param requestType the type of request (pending or rejected)
     * @param layout      the xml layout that will hold the request's data
     * @return a RecyclerView.Adapter for the specified type of request
     */
    default RecyclerView.Adapter<VH> getRequestAdapter(
            DatabaseReference userTypeRef,
            String requestType,
            int layout
    ) {
        /* Get a reference to either pending or rejected requests */
        DatabaseReference pendingUserRef = userTypeRef.child(requestType);

        /* Create a FireBaseRecyclerAdapter which automatically handles getting data from the
         * database to populate the RecyclerView */
        return new FirebaseRecyclerAdapter<T, VH>(getFirebaseRecyclerOptions(userTypeRef)) {

            @NonNull
            @Override
            public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return initViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(layout, parent, false));
            }

            // Makes list element for a specific user
            @Override
            protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull T model) {
                DatabaseReference currentUserRef = getRef(position);

                /* When the "Accept" button is clicked, add the user to the "approved" section
                 * of the database, and only once moved the request is deleted */
                holder.setAcceptOnClickListener(v -> {
                    userTypeRef.child("approved")
                            .push()
                            .setValue(model, (error, ref) -> {
                                if (error != null) {
                                    Log.e("firebase", "Failed to approve user");
                                    return;
                                }

                                // TODO (MOISE): SEND PUSH NOTIFICATION WHEN USER IS ACCEPTED (AS IN DELIVERABLE 2)
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String emailaddress = model.getEmail();
                                        List emailToSend = new ArrayList();
                                        emailToSend.add(emailaddress);
                                        try {
                                            GMail email = new GMail("appjavatest38@gmail.com", "apicguvtbdjfxemz", emailToSend, "Accepted", "Your registration request has been accepted.");
                                            email.createEmailMessage();
                                            email.sendEmail();
                                        } catch (Exception e) {
                                            Log.e("email", "Failed to send email");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                currentUserRef.removeValue();
                            });
                });

                /* If the request can be rejected, then when the "Reject" button is clicked, add the
                 * user to the "rejected" section of the database, and only once moved the request is
                 * deleted */
                if (holder instanceof RejectableRequest) {
                    ((RejectableRequest) holder).setRejectOnClickListener(v -> {
                        userTypeRef.child("rejected")
                                .push()
                                .setValue(model, (error, ref) -> {
                                    if (error != null) {
                                        Log.e("firebase", "Failed to reject user");
                                    }

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String emailaddress = model.getEmail();
                                            List emailToSend = new ArrayList();
                                            emailToSend.add(emailaddress);
                                            try {
                                                GMail email = new GMail("appjavatest38@gmail.com", "apicguvtbdjfxemz", emailToSend, "Refused", "Your registration request has been refused.");
                                                email.createEmailMessage();
                                                email.sendEmail();
                                            } catch (Exception e) {
                                                Log.e("email", "Failed to send email");
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();

                                    currentUserRef.removeValue();
                                });
                    });
                }

                /* Fill the view with the user's data */
                holder.bind(model);
            }
        };
    }

    /**
     * Creates an instance of FirebaseRecyclerOptions that are used to initialize the
     * FirebaseRecyclerAdapter
     *
     * @param userTypeRef a reference to the node representing the user's type.
     *                    Should be "users/attendees" or "users/organizers"
     * @return the correct FirebaseRecyclerOptions for the given types
     */
    FirebaseRecyclerOptions<T> getFirebaseRecyclerOptions(DatabaseReference userTypeRef);

    /**
     * Factory method to create an instance of the ViewHolder
     *
     * @param view a View used to initialize the ViewHolder
     * @return an instance of the ViewHolder
     */
    VH initViewHolder(View view);
}
