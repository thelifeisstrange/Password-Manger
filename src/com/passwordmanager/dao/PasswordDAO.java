package com.passwordmanager.dao;

import com.passwordmanager.model.Password;
import com.passwordmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Password entities
 */
public class PasswordDAO {

    /**
     * Creates a new password entry
     *
     * @param password The password object to create
     * @return The ID of the newly created password, or -1 if failed
     */
    public int createPassword(Password password) {
        String sql = "INSERT INTO passwords (user_id, title, username, password, website, notes) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, password.getUserId());
            pstmt.setString(2, password.getTitle());
            pstmt.setString(3, password.getUsername());
            pstmt.setString(4, password.getPassword());
            pstmt.setString(5, password.getWebsite());
            pstmt.setString(6, password.getNotes());
            
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
     * Gets all passwords for a specific user
     *
     * @param userId The user ID
     * @return List of passwords
     */
    public List<Password> getAllPasswordsByUserId(int userId) {
        List<Password> passwords = new ArrayList<>();
        String sql = "SELECT * FROM passwords WHERE user_id = ? ORDER BY title";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Password password = new Password(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("website"),
                    rs.getString("notes"),
                    rs.getString("last_updated")
                );
                passwords.add(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return passwords;
    }
    
    /**
     * Gets a specific password by ID
     *
     * @param id The password ID
     * @return The password, or null if not found
     */
    public Password getPasswordById(int id) {
        String sql = "SELECT * FROM passwords WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Password(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("website"),
                    rs.getString("notes"),
                    rs.getString("last_updated")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Updates an existing password
     *
     * @param password The password to update
     * @return true if successful, false otherwise
     */
    public boolean updatePassword(Password password) {
        String sql = "UPDATE passwords SET title = ?, username = ?, password = ?, website = ?, notes = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, password.getTitle());
            pstmt.setString(2, password.getUsername());
            pstmt.setString(3, password.getPassword());
            pstmt.setString(4, password.getWebsite());
            pstmt.setString(5, password.getNotes());
            pstmt.setInt(6, password.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a password by ID
     *
     * @param id The password ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deletePassword(int id) {
        String sql = "DELETE FROM passwords WHERE id = ?";
        
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