package com.example.pkiprojekat.models;

import java.io.Serializable;

public class Notification implements Serializable {
    private String id;
    private String title;
    private String message;
    private String date;
    private boolean isRead;
    private String type;
    
    public Notification() {
    }
    
    public Notification(String id, String title, String message, String date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
        this.isRead = false;
//        this.type = type;
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setRead(boolean read) {
        isRead = read;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}