package com.example.eams.attendee;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eams.R;
import com.example.eams.event.Event;

import java.util.ArrayList;

public class EventSearchAdapter extends RecyclerView.Adapter<EventSearchAdapter.ViewHolder> {

    private ArrayList<Event> events;
    private String attendeeKey;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.event_search_title);
            descriptionTextView = itemView.findViewById(R.id.event_search_description);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public void setOnClickListener(View.OnClickListener l) {
            itemView.setOnClickListener(l);
        }
    }

    public EventSearchAdapter(ArrayList<Event> events, String attendeeKey) {
        this.events = events;
        this.attendeeKey = attendeeKey;
    }

    @NonNull
    @Override
    public EventSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_event_search_result, parent, false);
        return new EventSearchAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventSearchAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.getTitleTextView().setText(event.getTitle());
        String description;

        /* Add ... if event description is more than 100 characters */
        if (event.getDescription().length() > 100) {
            description = event.getDescription().substring(0, 100) + "...";
        } else {
            description = event.getDescription();
        }
        holder.getDescriptionTextView().setText(description);

        holder.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ViewEventActivity.class);
            intent.putExtra("EventKey", event.getDatabaseKey());
            intent.putExtra("userDatabaseKey", attendeeKey);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(ArrayList<Event> events) {
        /* Changes the associated arraylist for the recyclerView
        and then updates accordingly */
        this.events = events;
        notifyDataSetChanged();
    }

}
