package com.passwordmanager.model;

/**
 * Model class for Note entries
 */
public class Note {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String lastUpdated;
    
    /**
     * Default constructor
     */
    public Note() {
    }
    
    /**
     * Constructor with all fields except id
     */
    public Note(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
    
    /**
     * Constructor with all fields
     */
    public Note(int id, int userId, String title, String content, String lastUpdated) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
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
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    @Override
    public String toString() {
        return "Note{" +
               "id=" + id +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", lastUpdated='" + lastUpdated + '\'' +
               '}';
    }
} 