package com.example.pkiprojekat.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.adapters.NotificationAdapter;
import com.example.pkiprojekat.models.Notification;
import com.example.pkiprojekat.utils.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewNotifications;
    private LinearLayout emptyNotificationsLayout;
    private TextView tvEmptyMessage;
    
    private SharedPrefsManager sharedPrefsManager;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        
        sharedPrefsManager = new SharedPrefsManager(this);
        
        initViews();
        setupToolbar();
        loadNotifications();
        setupRecyclerView();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        emptyNotificationsLayout = findViewById(R.id.emptyNotificationsLayout);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Obavestenja");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        }
    }

    private void loadNotifications() {
        notifications = sharedPrefsManager.getNotifications();
        
        // Add sample notifications if empty
        if (notifications.isEmpty()) {
            addSampleNotifications();
        }
        
        if (notifications.isEmpty()) {
            showEmptyState();
        } else {
            showNotifications();
        }
    }

    private void addSampleNotifications() {
        notifications = new ArrayList<>();
        
        Notification notification1 = new Notification(
            "1",
            "Dobrodošli u Trenuci za pamćenje!",
            "Hvala što ste se registrovali. Istražite naše ponude i kreirajte nezaboravne trenutke.",
                String.valueOf(System.currentTimeMillis())
        );
        
        Notification notification2 = new Notification(
            "2", 
            "Nova promocija!",
            "Otkrijte našu novu promociju za dečje rođendane sa popustom od 30%!",
                String.valueOf(System.currentTimeMillis() - 86400000) // 1 day ago
        );
        
        Notification notification3 = new Notification(
            "3",
            "Rezervacija potvrđena",
            "Vaša rezervacija za događaj 'Prvi rođendan' je uspešno potvrđena.",
                String.valueOf(System.currentTimeMillis() - 172800000) // 2 days ago
        );
        
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        
        sharedPrefsManager.saveNotifications(notifications);
    }

    private void showEmptyState() {
        recyclerViewNotifications.setVisibility(View.GONE);
        emptyNotificationsLayout.setVisibility(View.VISIBLE);
    }

    private void showNotifications() {
        recyclerViewNotifications.setVisibility(View.VISIBLE);
        emptyNotificationsLayout.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(this, notifications, new NotificationAdapter.OnNotificationClickListener() {
            @Override
            public void onNotificationClick(Notification notification) {
                markAsRead(notification);
            }
        });
        recyclerViewNotifications.setAdapter(notificationAdapter);
    }

    private void markAsRead(Notification notification) {
        if (!notification.isRead()) {
            notification.setRead(true);
            sharedPrefsManager.saveNotifications(notifications);
            notificationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}