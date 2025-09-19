package com.example.pkiprojekat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    public interface OnCartItemActionListener {
        void onRemoveItem(CartItem item);
        void onQuantityChanged(CartItem item, int newQuantity);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        
        holder.tvEventTitle.setText(item.getEvent().getTitle());
        holder.tvGuestCount.setText("Broj gostiju: " + item.getQuantity());
        holder.tvDate.setText("Datum: " + item.getEvent().getDate());
        
        // Set placeholder image or load from URL
        // For now using a placeholder
        holder.imgEvent.setImageResource(R.drawable.ic_notification);
        
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemoveItem(item);
                }
            }
        });
        
        // Add click listeners for quantity adjustment if needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Could implement quantity modification dialog here
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEvent;
        TextView tvEventTitle;
        TextView tvGuestCount;
        TextView tvDate;
        Button btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvGuestCount = itemView.findViewById(R.id.tvGuestCount);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}