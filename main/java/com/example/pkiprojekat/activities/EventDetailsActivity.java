package com.example.pkiprojekat.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.adapters.CommentsAdapter;
import com.example.pkiprojekat.models.CartItem;
import com.example.pkiprojekat.models.Comment;
import com.example.pkiprojekat.models.Event;
import com.example.pkiprojekat.models.User;
import com.example.pkiprojekat.utils.SharedPrefsManager;
import com.example.pkiprojekat.utils.Validation;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class EventDetailsActivity extends AppCompatActivity {

    private Event event;
    private SharedPrefsManager sharedPrefsManager;
    
    private ImageView ivEventImage;
    private TextView tvEventTitle, tvPrice, tvDescription;
    private TextInputEditText etDate, etGuestCount, etComment;
    private Button btnAddToCart, btnPostComment;
    private RatingBar ratingBar;
    private RecyclerView recyclerViewComments;
    private CommentsAdapter commentsAdapter;
    
    private Calendar selectedDate = Calendar.getInstance();
    private List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        
        sharedPrefsManager = new SharedPrefsManager(this);
        
        event = (Event) getIntent().getSerializableExtra("event");
        if (event == null) {
            finish();
            return;
        }
        
        initViews();
        setupToolbar();
        loadEventData();
        setupListeners();
        setupCommentsRecyclerView();
        loadComments();
    }

    private void initViews() {
        ivEventImage = findViewById(R.id.ivEventImage);
        tvEventTitle = findViewById(R.id.tvEventTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        etDate = findViewById(R.id.etDate);
        etGuestCount = findViewById(R.id.etGuestCount);
        etComment = findViewById(R.id.etComment);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnPostComment = findViewById(R.id.btnPostComment);
        ratingBar = findViewById(R.id.ratingBar);
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(event.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    private void loadEventData() {
        tvEventTitle.setText(event.getTitle() + " / do " + event.getGuestCount() + " gostiju");
        
        String priceText = "Cena: " + (int)(event.getPrice()/event.getGuestCount()) + "e hrana i piće + 10e dekoracija po stolici";
        tvPrice.setText(priceText);
        
        String description = "Opis: " + event.getDescription();
        tvDescription.setText(description);
        
        etGuestCount.setText(String.valueOf(Math.min(event.getGuestCount(), 120)));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy.", Locale.getDefault());
        etDate.setText(dateFormat.format(selectedDate.getTime()));
        
        int imageResource = getResources().getIdentifier(event.getImageUrl(), "drawable", getPackageName());
        if (imageResource != 0) {
            ivEventImage.setImageResource(imageResource);
        }
    }

    private void setupListeners() {
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy.", Locale.getDefault());
                    etDate.setText(dateFormat.format(selectedDate.getTime()));
                }
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void addToCart() {
        String guestCountStr = etGuestCount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        
        if (Validation.isFieldEmpty(guestCountStr)) {
            Toast.makeText(this, "Unesite broj gostiju", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int guestCount = Integer.parseInt(guestCountStr);
        if (guestCount <= 0 || guestCount > event.getGuestCount()) {
            Toast.makeText(this, "Broj gostiju mora biti između 1 i " + event.getGuestCount(), Toast.LENGTH_SHORT).show();
            return;
        }
        
        Event cartEvent = new Event();
        cartEvent.setId(event.getId());
        cartEvent.setTitle(event.getTitle());
        cartEvent.setDescription(event.getDescription());
        cartEvent.setImageUrl(event.getImageUrl());
        cartEvent.setGuestCount(guestCount);
        cartEvent.setPrice(event.getPrice());
        cartEvent.setDate(date);
        cartEvent.setLocation(event.getLocation());
        
        CartItem cartItem = new CartItem(UUID.randomUUID().toString(), cartEvent, 1);
        
        List<CartItem> cartItems = sharedPrefsManager.getCartItems();
        cartItems.add(cartItem);
        sharedPrefsManager.saveCartItems(cartItems);
        
        Toast.makeText(this, "Dodato u korpu!", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void setupCommentsRecyclerView() {
        commentsAdapter = new CommentsAdapter(comments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(commentsAdapter);
    }

    private void loadComments() {
        comments.clear();
        
        Comment comment1 = new Comment();
        comment1.setId("1");
        comment1.setEventId(event.getId());
        comment1.setUserName("milanZec");
        comment1.setText("Uthvatili smo promociju, što se tiče cene nismo mogli bolje proći! Sve pohvale za prostor i dekoraciju, svi smo zadovoljni :)");
        comment1.setDate("3.6.2025. 22:20");
        comment1.setRating(5);
        comments.add(comment1);
        
        List<Comment> savedComments = sharedPrefsManager.getComments(event.getId());
        comments.addAll(savedComments);
        
        commentsAdapter.notifyDataSetChanged();
    }

    private void postComment() {
        String commentText = etComment.getText().toString().trim();
        float rating = ratingBar.getRating();
        
        if (Validation.isFieldEmpty(commentText)) {
            Toast.makeText(this, "Unesite komentar", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (rating == 0) {
            Toast.makeText(this, "Izaberite ocenu", Toast.LENGTH_SHORT).show();
            return;
        }
        
        User currentUser = sharedPrefsManager.getCurrentUser();
        
        Comment newComment = new Comment();
        newComment.setId(UUID.randomUUID().toString());
        newComment.setEventId(event.getId());
        newComment.setUserName(currentUser != null ? currentUser.getUsername() : "Anonimus");
        newComment.setText(commentText);
        newComment.setRating(rating);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.yyyy. HH:mm", Locale.getDefault());
        newComment.setDate(dateFormat.format(new Date()));
        
        comments.add(newComment);
        sharedPrefsManager.saveComment(newComment);
        
        commentsAdapter.notifyItemInserted(comments.size() - 1);
        recyclerViewComments.scrollToPosition(comments.size() - 1);
        
        etComment.setText("");
        ratingBar.setRating(0);
        
        Toast.makeText(this, "Komentar je dodat!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}