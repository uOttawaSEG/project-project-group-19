package com.example.eams.attendee;

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
    }

    public EventSearchAdapter(ArrayList<Event> events) {
        this.events = events;
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

        if (event.getDescription().length() > 100) {
            description = event.getDescription().substring(0, 100) + "...";
        } else {
            description = event.getDescription();
        }
        holder.getDescriptionTextView().setText(description);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

}
