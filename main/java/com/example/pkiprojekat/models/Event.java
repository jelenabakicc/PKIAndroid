package com.example.pkiprojekat.models;

import java.io.Serializable;

public class Event implements Serializable {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int guestCount;
    private double price;
    private String date;
    private String location;
    
    public Event() {
    }
    
    public Event(String id, String title, String description, String imageUrl, int guestCount, double price, String date, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.guestCount = guestCount;
        this.price = price;
        this.date = date;
        this.location = location;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getGuestCount() {
        return guestCount;
    }
    
    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}