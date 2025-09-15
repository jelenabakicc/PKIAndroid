package com.example.pkiprojekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.User;
import com.example.pkiprojekat.utils.SharedPrefsManager;
import com.example.pkiprojekat.utils.Validation;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister, tvError;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefsManager = new SharedPrefsManager(this);

        initViews();
        setupListeners();
    }

    private void initViews(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvError = findViewById(R.id.tvError);
    }

    private void setupListeners(){
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                tryLogin();
            }
        });

    }

    private void tryLogin(){
        tvError.setVisibility(View.GONE);
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (Validation.isFieldEmpty(username)) {
            showError("Unesite korisničko ime");
            return;
        }

        if (Validation.isFieldEmpty(password)) {
            showError("Unesite lozinku");
            return;
        }
        User savedUser = sharedPrefsManager.getUserByUsername(username);

        if (savedUser == null) {
            showError("Korisnik ne postoji. Molimo registrujte se.");
            return;
        }

        if (savedUser.getPassword().equals(password)) {
            sharedPrefsManager.login(savedUser);
            Toast.makeText(this, "Uspešna prijava!", Toast.LENGTH_SHORT).show();
            navigateToHome();
        } else {
            showError("Pogrešno korisničko ime ili lozinka");
        }
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}