package com.example.pkiprojekat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.Event;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private List<Event> events;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    public EventsAdapter(List<Event> events, OnEventClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        
        holder.tvTitle.setText(event.getTitle());
        holder.tvDescription.setText(event.getDescription());
        holder.tvGuestCount.setText("Do " + event.getGuestCount() + " gostiju");
        holder.tvLocation.setText(event.getLocation());
        holder.tvDate.setText(event.getDate());
        
        // Format price with currency
        NumberFormat formatter = NumberFormat.getInstance(new Locale("sr", "RS"));
        holder.tvPrice.setText(formatter.format(event.getPrice()) + " RSD");
        
        // Set placeholder image based on event type
        setEventImage(holder.imgEvent, event.getImageUrl());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEventClick(event);
                }
            }
        });
    }
    
    private void setEventImage(ImageView imageView, String imageUrl) {
        // For now, using placeholder images based on event type
        int drawableResource = R.drawable.ic_notification; // default
        
        if (imageUrl != null) {
            switch (imageUrl.toLowerCase()) {
                case "birthday_event":
                    drawableResource = R.drawable.ic_notification;
                    break;
                case "wedding_small":
                case "wedding_large":
                    drawableResource = R.drawable.ic_notification;
                    break;
                case "coming_of_age":
                    drawableResource = R.drawable.ic_notification;
                    break;
                default:
                    drawableResource = R.drawable.ic_notification;
            }
        }
        
        imageView.setImageResource(drawableResource);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEvent;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvGuestCount;
        TextView tvLocation;
        TextView tvDate;
        TextView tvPrice;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvGuestCount = itemView.findViewById(R.id.tvGuestCount);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}