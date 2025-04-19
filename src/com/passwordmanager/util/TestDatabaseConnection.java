package com.passwordmanager.util;

/**
 * Simple test class to verify database connection
 */
public class TestDatabaseConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        boolean success = DatabaseUtil.testConnection();
        
        if (success) {
            System.out.println("Connection test SUCCESSFUL!");
        } else {
            System.out.println("Connection test FAILED!");
            System.out.println("Please check your database.properties file and make sure MySQL is running.");
        }
    }
} 