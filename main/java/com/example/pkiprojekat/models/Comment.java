package com.example.pkiprojekat.models;

import java.io.Serializable;

public class Comment implements Serializable {
    private String id;
    private String eventId;
    private String userName;
    private String text;
    private String date;
    private float rating;
    
    public Comment() {
    }
    
    public Comment(String id, String eventId, String userName, String text, String date, float rating) {
        this.id = id;
        this.eventId = eventId;
        this.userName = userName;
        this.text = text;
        this.date = date;
        this.rating = rating;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public float getRating() {
        return rating;
    }
    
    public void setRating(float rating) {
        this.rating = rating;
    }
}