package com.example.pkiprojekat.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.Notification;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notification> notifications;
    private OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
    }

    public NotificationAdapter(Context context, List<Notification> notifications, OnNotificationClickListener listener) {
        this.context = context;
        this.notifications = notifications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        
        holder.tvTitle.setText(notification.getTitle());
        holder.tvMessage.setText(notification.getMessage());

        // Format timestamp using java.time API
        LocalDateTime dateTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(Long.parseLong(notification.getDate())),
                    ZoneId.systemDefault()
            );
            DateTimeFormatter formatter = null;
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault());
            String formattedDate = dateTime.format(formatter);
            holder.tvTimestamp.setText(formattedDate);
        }
        
        // Show unread indicator
        if (notification.isRead()) {
            holder.unreadIndicator.setVisibility(View.GONE);
            holder.itemView.setAlpha(0.7f);
        } else {
            holder.unreadIndicator.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(1.0f);
        }
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNotificationClick(notification);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvMessage;
        TextView tvTimestamp;
        View unreadIndicator;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            unreadIndicator = itemView.findViewById(R.id.unreadIndicator);
        }
    }
}