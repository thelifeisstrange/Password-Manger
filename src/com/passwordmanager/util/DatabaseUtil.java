package com.passwordmanager.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Utility class for database operations
 */
public class DatabaseUtil {

    // MySQL connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/passwordmanager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Kulkarni"; // Set your MySQL password here if needed
    
    static {
        System.out.println("Using MySQL database at: " + DB_URL);
        
        // Initialize database on startup
        try {
            Connection conn = getConnection();
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a connection to the database
     *
     * @return A database connection
     * @throws SQLException If a database error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Create tables if they don't exist
            createTablesIfNeeded(connection);
            
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }
    
    /**
     * Creates the database tables if they don't exist
     *
     * @param connection The database connection
     * @throws SQLException If a database error occurs
     */
    private static void createTablesIfNeeded(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create database if not exists
            stmt.execute("CREATE DATABASE IF NOT EXISTS passwordmanager");
            
            // Use the database
            stmt.execute("USE passwordmanager");
            
            // Create users table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "phone VARCHAR(20), " +
                "password VARCHAR(255) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            
            // Create passwords table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS passwords (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT NOT NULL, " +
                "title VARCHAR(100) NOT NULL, " +
                "username VARCHAR(100), " +
                "password VARCHAR(255) NOT NULL, " +
                "website VARCHAR(255), " +
                "notes TEXT, " +
                "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")"
            );
            
            // Create notes table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS notes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT NOT NULL, " +
                "title VARCHAR(100) NOT NULL, " +
                "content TEXT, " +
                "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id)" +
                ")"
            );
        }
    }
    
    /**
     * Closes a database connection
     *
     * @param connection The connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Tests the database connection and displays MySQL information
     * This is useful for debugging database connectivity issues
     * 
     * @return true if the connection was successful
     */
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            
            // Get MySQL version
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                String version = rs.getString(1);
                System.out.println("MySQL version: " + version);
            }
            
            // Test database write
            stmt.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY)");
            stmt.execute("INSERT INTO test_table (id) VALUES (1) ON DUPLICATE KEY UPDATE id=id");
            stmt.execute("DELETE FROM test_table WHERE id = 1");
            
            System.out.println("Database connection test successful");
            System.out.println("Database URL: " + DB_URL);
            
            return true;
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }
} 