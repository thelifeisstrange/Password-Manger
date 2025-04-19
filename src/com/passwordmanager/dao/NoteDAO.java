package com.passwordmanager.dao;

import com.passwordmanager.model.Note;
import com.passwordmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Note entities
 */
public class NoteDAO {

    /**
     * Creates a new note entry
     *
     * @param note The note object to create
     * @return The ID of the newly created note, or -1 if failed
     */
    public int createNote(Note note) {
        String sql = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, note.getUserId());
            pstmt.setString(2, note.getTitle());
            pstmt.setString(3, note.getContent());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Gets all notes for a specific user
     *
     * @param userId The user ID
     * @return List of notes
     */
    public List<Note> getAllNotesByUserId(int userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE user_id = ? ORDER BY title";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Note note = new Note(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("last_updated")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return notes;
    }
    
    /**
     * Gets a specific note by ID
     *
     * @param id The note ID
     * @return The note, or null if not found
     */
    public Note getNoteById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Note(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("last_updated")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Updates an existing note
     *
     * @param note The note to update
     * @return true if successful, false otherwise
     */
    public boolean updateNote(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setInt(3, note.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a note by ID
     *
     * @param id The note ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteNote(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 