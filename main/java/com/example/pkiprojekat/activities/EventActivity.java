package com.example.pkiprojekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.adapters.EventsAdapter;
import com.example.pkiprojekat.models.Event;
import com.example.pkiprojekat.utils.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity implements EventsAdapter.OnEventClickListener {

    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        
        sharedPrefsManager = new SharedPrefsManager(this);
        
        if (!sharedPrefsManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }
        
        initViews();
        setupToolbar();
        setupRecyclerView();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewEvents);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ponude");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        List<Event> events = createSampleEvents();
        eventsAdapter = new EventsAdapter(events, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventsAdapter);
    }

    private List<Event> createSampleEvents() {
        List<Event> events = new ArrayList<>();
        
        Event birthdayEvent = new Event();
        birthdayEvent.setId("1");
        birthdayEvent.setTitle("Prvi rođendan");
        birthdayEvent.setDescription("Organizacija prvog rođendana sa kompletnom dekoracijom i animatorom");
        birthdayEvent.setGuestCount(50);
        birthdayEvent.setPrice(25000);
        birthdayEvent.setDate("Dostupno tokom cele godine");
        birthdayEvent.setLocation("Sala 'Bajka'");
        birthdayEvent.setImageUrl("birthday_event");
        events.add(birthdayEvent);
        
        Event weddingSmall = new Event();
        weddingSmall.setId("2");
        weddingSmall.setTitle("Venčanje - do 50 gostiju");
        weddingSmall.setDescription("Intimno venčanje sa dekoracijom, muzikom i kompletnim servisom");
        weddingSmall.setGuestCount(50);
        weddingSmall.setPrice(80000);
        weddingSmall.setDate("Dostupno tokom cele godine");
        weddingSmall.setLocation("Sala 'Romantika'");
        weddingSmall.setImageUrl("wedding_small");
        events.add(weddingSmall);
        
        Event weddingLarge = new Event();
        weddingLarge.setId("3");
        weddingLarge.setTitle("Venčanje - preko 50 gostiju");
        weddingLarge.setDescription("Veliko venčanje sa luksuznom dekoracijom, bendom i premium servisom");
        weddingLarge.setGuestCount(150);
        weddingLarge.setPrice(150000);
        weddingLarge.setDate("Dostupno tokom cele godine");
        weddingLarge.setLocation("Velika sala 'Elegance'");
        weddingLarge.setImageUrl("wedding_large");
        events.add(weddingLarge);
        
        Event comingOfAge = new Event();
        comingOfAge.setId("4");
        comingOfAge.setTitle("Punoletstvo - do 150 gostiju");
        comingOfAge.setDescription("Nezaboravan 18. rođendan sa DJ-om, dekoracijom i foto booth-om");
        comingOfAge.setGuestCount(150);
        comingOfAge.setPrice(60000);
        comingOfAge.setDate("Dostupno tokom cele godine");
        comingOfAge.setLocation("Sala 'Party Zone'");
        comingOfAge.setImageUrl("coming_of_age");
        events.add(comingOfAge);
        
        return events;
    }

    @Override
    public void onEventClick(Event event) {
        // Navigate to event details
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}