package com.example.pkiprojekat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.Promotion;

import java.util.List;

public class PromotionPagerAdapter extends RecyclerView.Adapter<PromotionPagerAdapter.PromotionViewHolder> {

    private List<Promotion> promotions;

    public PromotionPagerAdapter(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = promotions.get(position);
        
        holder.tvTitle.setText(promotion.getTitle());
        holder.tvDescription.setText(promotion.getDescription());
        holder.tvDiscount.setText(promotion.getDiscountPercent() + "% POPUSTA");
        holder.tvValidUntil.setText("Važi do: " + promotion.getValidUntil());
        
        // Set placeholder image based on promotion type
        setPromotionImage(holder.imgPromotion, promotion.getImageUrl());
    }
    
    private void setPromotionImage(ImageView imageView, String imageUrl) {
        // For now, using placeholder images based on promotion type
        int drawableResource = R.drawable.ic_notification; // default
        
        if (imageUrl != null) {
            switch (imageUrl.toLowerCase()) {
                case "birthday_promotion":
                    drawableResource = R.drawable.ic_notification;
                    break;
                case "wedding_promotion":
                    drawableResource = R.drawable.ic_notification;
                    break;
                case "coming_age_promotion":
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
        return promotions.size();
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPromotion;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDiscount;
        TextView tvValidUntil;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPromotion = itemView.findViewById(R.id.imgPromotion);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvValidUntil = itemView.findViewById(R.id.tvValidUntil);
        }
    }
}