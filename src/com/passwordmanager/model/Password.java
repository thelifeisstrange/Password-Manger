package com.passwordmanager.model;

/**
 * Model class for Password entries
 */
public class Password {
    private int id;
    private int userId;
    private String title;
    private String username;
    private String password;
    private String website;
    private String notes;
    private String lastUpdated;
    
    /**
     * Default constructor
     */
    public Password() {
    }
    
    /**
     * Constructor with all fields except id
     */
    public Password(int userId, String title, String username, String password, String website, String notes) {
        this.userId = userId;
        this.title = title;
        this.username = username;
        this.password = password;
        this.website = website;
        this.notes = notes;
    }
    
    /**
     * Constructor with all fields
     */
    public Password(int id, int userId, String title, String username, String password, String website, String notes, String lastUpdated) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.username = username;
        this.password = password;
        this.website = website;
        this.notes = notes;
        this.lastUpdated = lastUpdated;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
} 