package com.passwordmanager;

import com.passwordmanager.ui.WelcomeScreen;
import com.passwordmanager.util.DatabaseUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Main application class for the Password Manager
 */
public class PasswordManagerApp {
    
    /**
     * Application entry point
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Set the look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system look and feel: " + e.getMessage());
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Check MySQL connectivity before launching the app
                if (!checkDatabaseConnectivity()) {
                    return; // Exit if database connection failed
                }
                
                // Start with the welcome screen
                new WelcomeScreen().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Error starting application: " + e.getMessage(),
                    "Application Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
    /**
     * Checks database connectivity and shows a helpful message if it fails
     * 
     * @return true if database is accessible, false otherwise
     */
    private static boolean checkDatabaseConnectivity() {
        try {
            if (!DatabaseUtil.testConnection()) {
                showDatabaseErrorDialog();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            showDatabaseErrorDialog();
            return false;
        }
    }
    
    /**
     * Shows a detailed error dialog with instructions on how to fix database issues
     */
    private static void showDatabaseErrorDialog() {
        String message = 
            "<html><body width='450'>" +
            "<h2>Database Connection Error</h2>" +
            "<p>Unable to connect to the MySQL database. Please check:</p>" +
            "<ol>" +
            "<li>MySQL is installed and running</li>" +
            "<li>The database 'passwordmanager' exists</li>" +
            "<li>The connection settings in DatabaseUtil.java are correct</li>" +
            "</ol>" +
            "<p>You can run the setup script to automatically configure the database:</p>" +
            "<ul>" +
            "<li>On Linux/macOS: <code>./setup.sh</code></li>" +
            "<li>On Windows: <code>setup.bat</code></li>" +
            "</ul>" +
            "<p>For more details, please refer to the README.md file.</p>" +
            "</body></html>";
        
        JOptionPane optionPane = new JOptionPane(
            new JLabel(message),
            JOptionPane.ERROR_MESSAGE
        );
        
        JDialog dialog = optionPane.createDialog("Database Connection Error");
        dialog.setSize(500, 400);
        dialog.setVisible(true);
    }
} 