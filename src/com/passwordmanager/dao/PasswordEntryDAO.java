package com.passwordmanager.dao;

import com.passwordmanager.model.PasswordEntry;
import com.passwordmanager.util.DatabaseUtil;
import com.passwordmanager.util.EncryptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for PasswordEntry-related database operations
 */
public class PasswordEntryDAO {
    
    /**
     * Creates a new password entry in the database
     * 
     * @param entry The password entry to add
     * @return The ID of the new entry, or -1 if an error occurred
     */
    public int createPasswordEntry(PasswordEntry entry) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int entryId = -1;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            // Encrypt password before storing
            String encryptedPassword = EncryptionUtil.encrypt(entry.getPassword());
            
            String sql = "INSERT INTO password_entries (user_id, website, username, password, notes, category) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entry.getUserId());
            statement.setString(2, entry.getWebsite());
            statement.setString(3, entry.getUsername());
            statement.setString(4, encryptedPassword);
            statement.setString(5, entry.getNotes());
            statement.setString(6, entry.getCategory());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    entryId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating password entry: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return entryId;
    }
    
    /**
     * Retrieves all password entries for a user
     * 
     * @param userId The ID of the user
     * @return A list of password entries
     */
    public List<PasswordEntry> getPasswordEntriesByUserId(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<PasswordEntry> entries = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            
            String sql = "SELECT * FROM password_entries WHERE user_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PasswordEntry entry = new PasswordEntry();
                entry.setId(resultSet.getInt("id"));
                entry.setUserId(resultSet.getInt("user_id"));
                entry.setWebsite(resultSet.getString("website"));
                entry.setUsername(resultSet.getString("username"));
                
                // Decrypt password for use in the application
                String encryptedPassword = resultSet.getString("password");
                entry.setPassword(EncryptionUtil.decrypt(encryptedPassword));
                
                entry.setNotes(resultSet.getString("notes"));
                entry.setCategory(resultSet.getString("category"));
                entry.setCreatedAt(resultSet.getString("created_at"));
                entry.setUpdatedAt(resultSet.getString("updated_at"));
                
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving password entries: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return entries;
    }
    
    /**
     * Retrieves password entries for a user filtered by website or category
     * 
     * @param userId The ID of the user
     * @param filter The filter string to search for in website or category
     * @return A list of matching password entries
     */
    public List<PasswordEntry> getPasswordEntriesByFilter(int userId, String filter) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<PasswordEntry> entries = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            
            String sql = "SELECT * FROM password_entries WHERE user_id = ? AND (website LIKE ? OR category LIKE ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, "%" + filter + "%");
            statement.setString(3, "%" + filter + "%");
            
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PasswordEntry entry = new PasswordEntry();
                entry.setId(resultSet.getInt("id"));
                entry.setUserId(resultSet.getInt("user_id"));
                entry.setWebsite(resultSet.getString("website"));
                entry.setUsername(resultSet.getString("username"));
                
                // Decrypt password for use in the application
                String encryptedPassword = resultSet.getString("password");
                entry.setPassword(EncryptionUtil.decrypt(encryptedPassword));
                
                entry.setNotes(resultSet.getString("notes"));
                entry.setCategory(resultSet.getString("category"));
                entry.setCreatedAt(resultSet.getString("created_at"));
                entry.setUpdatedAt(resultSet.getString("updated_at"));
                
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving password entries: " + e.getMessage());
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return entries;
    }
    
    /**
     * Updates a password entry in the database
     * 
     * @param entry The password entry to update
     * @return True if the update was successful, false otherwise
     */
    public boolean updatePasswordEntry(PasswordEntry entry) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            // Encrypt password before storing
            String encryptedPassword = EncryptionUtil.encrypt(entry.getPassword());
            
            String sql = "UPDATE password_entries SET website = ?, username = ?, password = ?, " +
                         "notes = ?, category = ? WHERE id = ? AND user_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, entry.getWebsite());
            statement.setString(2, entry.getUsername());
            statement.setString(3, encryptedPassword);
            statement.setString(4, entry.getNotes());
            statement.setString(5, entry.getCategory());
            statement.setInt(6, entry.getId());
            statement.setInt(7, entry.getUserId());
            
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password entry: " + e.getMessage());
        } finally {
            closeResources(null, statement, connection);
        }
        
        return success;
    }
    
    /**
     * Deletes a password entry from the database
     * 
     * @param entryId The ID of the entry to delete
     * @param userId The ID of the user (for security verification)
     * @return True if the deletion was successful, false otherwise
     */
    public boolean deletePasswordEntry(int entryId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            String sql = "DELETE FROM password_entries WHERE id = ? AND user_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entryId);
            statement.setInt(2, userId);
            
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting password entry: " + e.getMessage());
        } finally {
            closeResources(null, statement, connection);
        }
        
        return success;
    }
    
    /**
     * Closes database resources safely
     */
    private void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            DatabaseUtil.closeConnection(connection);
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
} 