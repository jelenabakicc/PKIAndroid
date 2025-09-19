package com.example.pkiprojekat.models;

import java.io.Serializable;

public class Promotion implements Serializable {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String validUntil;
    private int discountPercent;
    
    public Promotion() {
    }
    
    public Promotion(String id, String title, String description, String imageUrl, String validUntil, int discountPercent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.validUntil = validUntil;
        this.discountPercent = discountPercent;
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
    
    public String getValidUntil() {
        return validUntil;
    }
    
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
    
    public int getDiscountPercent() {
        return discountPercent;
    }
    
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
}