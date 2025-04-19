package com.passwordmanager.dao;

import com.passwordmanager.model.User;
import com.passwordmanager.util.DatabaseUtil;
import com.passwordmanager.util.EncryptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data Access Object for User-related database operations
 */
public class UserDAO {
    
    /**
     * Creates a new user in the database
     * 
     * @param user The user to add
     * @return The ID of the new user, or -1 if an error occurred
     * @throws RuntimeException If there's a database error or email already exists
     */
    public int createUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = -1;
        
        try {
            // First check if email already exists
            if (emailExists(user.getEmail())) {
                throw new RuntimeException("Email already exists. Please choose another email or login.");
            }
            
            connection = DatabaseUtil.getConnection();
            
            // Encrypt password before storing
            String encryptedPassword = EncryptionUtil.encrypt(user.getPassword());
            
            String sql = "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, encryptedPassword);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Check for unique constraint violation
            if (e.getMessage().contains("UNIQUE constraint failed") || 
                e.getMessage().contains("unique constraint") ||
                e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("Email already exists. Please choose another email or login.", e);
            } else {
                throw new RuntimeException("Error creating user: " + e.getMessage(), e);
            }
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return userId;
    }
    
    /**
     * Retrieves a user by email
     * 
     * @param email The email to search for
     * @return The User object if found, or null if not found
     */
    public User getUserByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            String sql = "SELECT * FROM users WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password")); // Still encrypted
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(resultSet, statement, connection);
        }
        
        return user;
    }
    
    /**
     * Authenticates a user with email and password
     * 
     * @param email The user's email
     * @param password The user's password (plain text)
     * @return The authenticated User object if successful, or null if authentication failed
     */
    public User authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        
        if (user != null) {
            String decryptedPassword = EncryptionUtil.decrypt(user.getPassword());
            if (decryptedPassword != null && decryptedPassword.equals(password)) {
                // Don't return the password to the application
                user.setPassword(null);
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Updates a user in the database
     * 
     * @param user The user to update
     * @return True if the update was successful, false otherwise
     */
    public boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setInt(4, user.getId());
            
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(null, statement, connection);
        }
        
        return success;
    }
    
    /**
     * Updates a user's password
     * 
     * @param userId The ID of the user
     * @param newPassword The new password (plain text)
     * @return True if the update was successful, false otherwise
     */
    public boolean updatePassword(int userId, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            // Encrypt password before storing
            String encryptedPassword = EncryptionUtil.encrypt(newPassword);
            
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, encryptedPassword);
            statement.setInt(2, userId);
            
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(null, statement, connection);
        }
        
        return success;
    }
    
    /**
     * Updates a user's password by email
     * 
     * @param email The email of the user
     * @param newPassword The new password (plain text)
     * @return True if the update was successful, false otherwise
     */
    public boolean resetPasswordByEmail(String email, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            // Get user by email first to validate they exist
            User user = getUserByEmail(email);
            if (user == null) {
                return false;
            }
            
            // Encrypt password before storing
            String encryptedPassword = EncryptionUtil.encrypt(newPassword);
            
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, encryptedPassword);
            statement.setString(2, email);
            
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error resetting password: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(null, statement, connection);
        }
        
        return success;
    }
    
    /**
     * Checks if an email exists in the database
     * 
     * @param email The email to check
     * @return True if the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return getUserByEmail(email) != null;
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