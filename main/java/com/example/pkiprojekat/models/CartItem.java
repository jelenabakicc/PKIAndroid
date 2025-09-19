package com.example.pkiprojekat.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String id;
    private Event event;
    private int quantity;
    private double totalPrice;
    private String addedDate;
    
    public CartItem() {
    }
    
    public CartItem(String id, Event event, int quantity) {
        this.id = id;
        this.event = event;
        this.quantity = quantity;
        this.totalPrice = event.getPrice() * quantity;
        this.addedDate = new java.util.Date().toString();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Event getEvent() {
        return event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
        updateTotalPrice();
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice();
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getAddedDate() {
        return addedDate;
    }
    
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
    
    private void updateTotalPrice() {
        if (event != null) {
            this.totalPrice = event.getPrice() * quantity;
        }
    }
}