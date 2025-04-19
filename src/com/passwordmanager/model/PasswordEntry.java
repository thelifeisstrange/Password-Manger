package com.passwordmanager.model;

/**
 * Model class to represent a password entry
 */
public class PasswordEntry {
    private int id;
    private int userId;
    private String website;
    private String username;
    private String password; // This will be stored encrypted
    private String notes;
    private String category;
    private String createdAt;
    private String updatedAt;
    
    public PasswordEntry() {
    }
    
    public PasswordEntry(int userId, String website, String username, String password) {
        this.userId = userId;
        this.website = website;
        this.username = username;
        this.password = password;
    }
    
    public PasswordEntry(int userId, String website, String username, String password, String notes, String category) {
        this.userId = userId;
        this.website = website;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.category = category;
    }
    
    public PasswordEntry(int id, int userId, String website, String username, String password, 
                         String notes, String category, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.website = website;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
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
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "PasswordEntry{" +
               "id=" + id +
               ", userId=" + userId +
               ", website='" + website + '\'' +
               ", username='" + username + '\'' +
               ", category='" + category + '\'' +
               ", createdAt='" + createdAt + '\'' +
               '}';
    }
} 