package com.example.pkiprojekat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.adapters.PromotionPagerAdapter;
import com.example.pkiprojekat.models.Promotion;
import com.example.pkiprojekat.utils.SharedPrefsManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class PromotionsActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private PromotionPagerAdapter promotionAdapter;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        
        sharedPrefsManager = new SharedPrefsManager(this);
        
        if (!sharedPrefsManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }
        
        initViews();
        setupToolbar();
        setupViewPager();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Promocije");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager() {
        List<Promotion> promotions = createSamplePromotions();
        promotionAdapter = new PromotionPagerAdapter(promotions);
        viewPager.setAdapter(promotionAdapter);
        
        new TabLayoutMediator(tabLayout, viewPager, 
            (tab, position) -> tab.setText("Promocija " + (position + 1))
        ).attach();
    }

    private List<Promotion> createSamplePromotions() {
        List<Promotion> promotions = new ArrayList<>();
        
        Promotion promotion1 = new Promotion();
        promotion1.setId("1");
        promotion1.setTitle("Prvi rođendan");
        promotion1.setDescription("Uz rezervaciju do kraja jula, dobijate 20% popusta po stolici, kao i 50% popusta na dekoraciju");
        promotion1.setDiscountPercent(20);
        promotion1.setValidUntil("31.07.2024");
        promotion1.setImageUrl("birthday_promotion");
        promotions.add(promotion1);
        
        Promotion promotion2 = new Promotion();
        promotion2.setId("2");
        promotion2.setTitle("Venčanje iz snova");
        promotion2.setDescription("Specijalna ponuda za venčanja sa preko 100 gostiju - besplatna dekoracija i 15% popusta");
        promotion2.setDiscountPercent(15);
        promotion2.setValidUntil("31.08.2024");
        promotion2.setImageUrl("wedding_promotion");
        promotions.add(promotion2);
        
        Promotion promotion3 = new Promotion();
        promotion3.setId("3");
        promotion3.setTitle("Punoletstvo");
        promotion3.setDescription("Za sve punoletstva rezervisana do kraja meseca - 25% popusta na kompletnu uslugu");
        promotion3.setDiscountPercent(25);
        promotion3.setValidUntil("30.09.2024");
        promotion3.setImageUrl("coming_age_promotion");
        promotions.add(promotion3);
        
        return promotions;
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