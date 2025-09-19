package com.example.pkiprojekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.User;
import com.example.pkiprojekat.utils.SharedPrefsManager;
import com.example.pkiprojekat.utils.Validation;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPrefsManager sharedPrefsManager;
    private User currentUser;
    private TextInputEditText etUsername, etName, etSurname, etAddress, etPhone;
    private Button btnSave;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int notificationCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefsManager = new SharedPrefsManager(this);
        currentUser = sharedPrefsManager.getCurrentUser();
        if (currentUser == null) {
            navigateToLogin();
            return;
        }

        initViews();
        setupToolbar();
        setupNavigationDrawer();
        loadUserData();
        setupListeners();
    }

    private void initViews(){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupToolbar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle("Moj profil");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        }
    }

    private void loadUserData(){
        if (currentUser != null) {
            etUsername.setText(currentUser.getUsername());
            etName.setText(currentUser.getName());
            etSurname.setText(currentUser.getSurname());
            etAddress.setText(currentUser.getAddress());
            etPhone.setText(currentUser.getPhone());
        }
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderSubtitle = headerView.findViewById(R.id.navHeaderSubtitle);
        if (currentUser != null) {
            navHeaderSubtitle.setText("Dobrodošli, " + currentUser.getName());
        }
        // Handle back press with OnBackPressedCallback
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // If we don't have a drawer open, let the system handle back press
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });
    }

    private void setupListeners(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void saveUserData(){
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (Validation.isFieldEmpty(name)) {
            Toast.makeText(this, "Ime je obavezno polje", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Validation.isFieldEmpty(surname)) {
            Toast.makeText(this, "Prezime je obavezno polje", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Validation.isFieldEmpty(address)) {
            Toast.makeText(this, "Adresa je obavezno polje", Toast.LENGTH_SHORT).show();
            return;
        }

        String phoneError = Validation.getFieldError("Telefon", phone);
        if (phoneError != null) {
            Toast.makeText(this, phoneError, Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setAddress(address);
        currentUser.setPhone(phone);

        sharedPrefsManager.saveUserData(currentUser);
        Toast.makeText(this, "Profil uspešno ažuriran", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        sharedPrefsManager.logout();
        navigateToLogin();
    }

    private void navigateToLogin(){
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_promocije) {
            startActivity(new Intent(this, PromotionsActivity.class));
        } else if (id == R.id.nav_ponude) {
            startActivity(new Intent(this, EventActivity.class));
        } else if (id == R.id.nav_korpa) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_kontakt) {
            startActivity(new Intent(this, ContactActivity.class));
        } else if (id == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem notificationItem = menu.findItem(R.id.action_notifications);
        View actionView = notificationItem.getActionView();

        if (actionView != null) {
            TextView badgeCounter = actionView.findViewById(R.id.badge_counter);
            if (notificationCount > 0) {
                badgeCounter.setText(String.valueOf(notificationCount));
                badgeCounter.setVisibility(View.VISIBLE);
            } else {
                badgeCounter.setVisibility(View.GONE);
            }

            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notifications) {
            startActivity(new Intent(this, NotificationsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}