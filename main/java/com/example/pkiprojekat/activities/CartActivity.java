package com.example.pkiprojekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.adapters.CartAdapter;
import com.example.pkiprojekat.models.CartItem;
import com.example.pkiprojekat.utils.SharedPrefsManager;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout emptyCartLayout;
    private LinearLayout fullCartLayout;
    private RecyclerView recyclerViewCart;
    private TextView tvTotalPrice;
    private TextView tvDiscountedPrice;
    private TextView tvFinalPrice;
    private Button btnCompleteReservation;
    
    private SharedPrefsManager sharedPrefsManager;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    
    private static final double DISCOUNT_RATE = 0.28; // 28% discount

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        sharedPrefsManager = new SharedPrefsManager(this);
        
        initViews();
        setupToolbar();
        loadCartData();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        emptyCartLayout = findViewById(R.id.emptyCartLayout);
        fullCartLayout = findViewById(R.id.fullCartLayout);
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDiscountedPrice = findViewById(R.id.tvDiscountedPrice);
        tvFinalPrice = findViewById(R.id.tvFinalPrice);
        btnCompleteReservation = findViewById(R.id.btnCompleteReservation);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Pregled korpe");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        }
    }

    private void loadCartData() {
        cartItems = sharedPrefsManager.getCartItems();
        
        if (cartItems.isEmpty()) {
            showEmptyCartState();
        } else {
            showFullCartState();
        }
    }

    private void showEmptyCartState() {
        emptyCartLayout.setVisibility(View.VISIBLE);
        fullCartLayout.setVisibility(View.GONE);
    }

    private void showFullCartState() {
        emptyCartLayout.setVisibility(View.GONE);
        fullCartLayout.setVisibility(View.VISIBLE);
        
        setupRecyclerView();
        calculatePrices();
    }

    private void setupRecyclerView() {
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onRemoveItem(CartItem item) {
                removeCartItem(item);
            }
            
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                updateItemQuantity(item, newQuantity);
            }
        });
        recyclerViewCart.setAdapter(cartAdapter);
    }

    private void calculatePrices() {
        double totalPrice = 0;
        
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        
        double discountAmount = totalPrice * DISCOUNT_RATE;
        double finalPrice = totalPrice - discountAmount;
        
        tvTotalPrice.setText(String.format("Ukupna cena: %.0f€", totalPrice));
        tvDiscountedPrice.setText(String.format("Cena sa popustom: %.0f€", finalPrice));
        tvFinalPrice.setText(String.format("Vrednost korpe: %.0f€", finalPrice));
    }

    private void removeCartItem(CartItem item) {
        sharedPrefsManager.removeCartItem(item.getId());
        cartItems.remove(item);
        
        if (cartItems.isEmpty()) {
            showEmptyCartState();
        } else {
            cartAdapter.notifyDataSetChanged();
            calculatePrices();
        }
        
        Toast.makeText(this, "Stavka uklonjena iz korpe", Toast.LENGTH_SHORT).show();
    }

    private void updateItemQuantity(CartItem item, int newQuantity) {
        if (newQuantity <= 0) {
            removeCartItem(item);
            return;
        }
        
        item.setQuantity(newQuantity);
        sharedPrefsManager.saveCartItems(cartItems);
        cartAdapter.notifyDataSetChanged();
        calculatePrices();
    }

    private void setupListeners() {
        btnCompleteReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeReservation();
            }
        });
    }

    private void completeReservation() {
        // Clear cart
        sharedPrefsManager.clearCart();
        cartItems.clear();
        
        // Show success message
        Toast.makeText(this, "Rezervacija uspešno završena!", Toast.LENGTH_LONG).show();
        
        // Show empty cart state
        showEmptyCartState();
        
        // Optionally navigate back to main activity
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}