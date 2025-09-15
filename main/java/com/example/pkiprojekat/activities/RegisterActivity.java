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

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etPassword, etConfirmPassword, etName, etSurname, etAddress, etPhone;
    private Button btnRegister;
    private TextView tvLogin, tvError;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPrefsManager = new SharedPrefsManager(this);

        initViews();
        setupListeners();
    }

    private void initViews(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        tvError = findViewById(R.id.tvError);
    }

    private void setupListeners(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRegister();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void tryRegister(){
        tvError.setVisibility(View.GONE);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String error = Validation.getFieldError("Korisničko ime", username);
        if (error != null) {
            showError(error);
            return;
        }

        error = Validation.getFieldError("Lozinka", password);
        if (error != null) {
            showError(error);
            return;
        }

        if (!Validation.doPasswordsMatch(password, confirmPassword)) {
            showError("Lozinke se ne poklapaju");
            return;
        }

        if (Validation.isFieldEmpty(name)) {
            showError("Ime je obavezno polje");
            return;
        }

        if (Validation.isFieldEmpty(surname)) {
            showError("Prezime je obavezno polje");
            return;
        }

        if (Validation.isFieldEmpty(address)) {
            showError("Adresa je obavezno polje");
            return;
        }

        error = Validation.getFieldError("Telefon", phone);
        if (error != null) {
            showError(error);
            return;
        }

        User existingUser = sharedPrefsManager.getUserByUsername(username);
        if (existingUser != null) {
            showError("Korisnik sa tim korisničkim imenom već postoji");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setAddress(address);
        newUser.setPhone(phone);

        sharedPrefsManager.addUser(newUser);

        Toast.makeText(this, "Uspešna registracija! Možete se prijaviti.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}