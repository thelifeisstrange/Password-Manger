package com.passwordmanager.ui;

import com.passwordmanager.dao.UserDAO;
import com.passwordmanager.model.User;
import com.passwordmanager.util.DataInitializer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;

/**
 * Signup screen for user registration
 */
public class SignupScreen extends JFrame {
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    
    /**
     * Constructor - Sets up the signup screen UI
     */
    public SignupScreen() {
        // Set up the frame
        UIUtil.setupFrame(this, "SecureVault - Sign Up", 650, 750);
        
        // Make the window full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Add window resize listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
            }
        });
        
        // Create header panel with back button and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Add back button
        JButton backButton = new JButton("← Back");
        backButton.setFont(UIUtil.BUTTON_FONT);
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this::backAction);
        headerPanel.add(backButton, BorderLayout.WEST);
        
        // Add title
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(UIUtil.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Create main card panel
        JPanel cardPanel = UIUtil.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 40, 40, 40));
        
        // Add card header with icon
        JLabel iconLabel = new JLabel("✍️", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 72));
        iconLabel.setForeground(UIUtil.ACCENT_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconLabel);
        cardPanel.add(Box.createVerticalStrut(5));
        
        // Add subtitle
        JLabel subtitleLabel = new JLabel("Your Details");
        subtitleLabel.setFont(UIUtil.SUBTITLE_FONT);
        subtitleLabel.setForeground(UIUtil.TEXT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(subtitleLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Dynamically adjust form width based on parent container
        formPanel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && formPanel.isShowing()) {
                Container parent = formPanel.getParent();
                if (parent != null) {
                    int width = Math.max(300, parent.getWidth() - 80);  // Subtract padding
                    formPanel.setMaximumSize(new Dimension(width, 600));
                    formPanel.revalidate();
                }
            }
        });
        
        // Add name field
        nameField = UIUtil.createTextField("Enter name");
        JPanel namePanel = UIUtil.createFormRow("Name", nameField);
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add email field
        emailField = UIUtil.createTextField("Enter email");
        JPanel emailPanel = UIUtil.createFormRow("Email", emailField);
        formPanel.add(emailPanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add phone field
        phoneField = UIUtil.createTextField("Enter phone");
        JPanel phonePanel = UIUtil.createFormRow("Phone", phoneField);
        formPanel.add(phonePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add password field
        passwordField = UIUtil.createPasswordField("Enter password");
        JPanel passwordPanel = UIUtil.createFormRow("Password", passwordField);
        formPanel.add(passwordPanel);
        
        // Add password requirements note
        JLabel passwordRequirements = new JLabel("<html>Min 8 chars with letters, numbers, symbols</html>");
        passwordRequirements.setFont(UIUtil.SMALL_FONT);
        passwordRequirements.setForeground(UIUtil.SECONDARY_TEXT_COLOR);
        passwordRequirements.setBorder(new EmptyBorder(5, 5, 10, 5));
        passwordRequirements.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordRequirements);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Add confirm password field
        confirmPasswordField = UIUtil.createPasswordField("Confirm password");
        JPanel confirmPasswordPanel = UIUtil.createFormRow("Confirm", confirmPasswordField);
        formPanel.add(confirmPasswordPanel);
        formPanel.add(Box.createVerticalStrut(30));
        
        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create sign up button
        JButton signupButton = UIUtil.createButton("Sign Up", this::signupAction);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Make signup button width responsive
        signupButton.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && signupButton.isShowing()) {
                Container parent = signupButton.getParent();
                if (parent != null) {
                    int width = Math.min(220, Math.max(180, parent.getWidth() / 2));
                    signupButton.setMaximumSize(new Dimension(width, UIUtil.BUTTON_SIZE.height));
                    signupButton.setPreferredSize(new Dimension(width, UIUtil.BUTTON_SIZE.height));
                    signupButton.revalidate();
                }
            }
        });
        
        buttonPanel.add(signupButton);
        formPanel.add(buttonPanel);
        
        // Add login prompt
        JPanel loginPromptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPromptPanel.setOpaque(false);
        loginPromptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel loginLabel = new JLabel("Have an account?");
        loginLabel.setFont(UIUtil.SMALL_FONT);
        loginLabel.setForeground(UIUtil.SECONDARY_TEXT_COLOR);
        
        JButton loginButton = new JButton("Log in here");
        loginButton.setFont(UIUtil.SMALL_FONT);
        loginButton.setForeground(UIUtil.PRIMARY_COLOR);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this::loginAction);
        
        loginPromptPanel.add(loginLabel);
        loginPromptPanel.add(loginButton);
        
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(loginPromptPanel);
        
        cardPanel.add(formPanel);
        
        // Create responsive wrapper panel for automatic centering and sizing
        JPanel centerPanel = UIUtil.createResponsivePanel(cardPanel);
        
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        
        // Set minimum size for the frame
        setMinimumSize(new Dimension(350, 600));
    }
    
    /**
     * Action handler for the signup button
     */
    private void signupAction(ActionEvent e) {
        // Get the user input
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate input
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            UIUtil.showError(this, "Fill all fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            UIUtil.showError(this, "Passwords don't match");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        if (password.length() < 8) {
            UIUtil.showError(this, "Password too short");
            passwordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        // Create a new user
        User user = new User(name, email, phone, password);
        
        // Save the user to the database
        UserDAO userDAO = new UserDAO();
        try {
            int userId = userDAO.createUser(user);
            
            if (userId > 0) {
                // Set the user ID
                user.setId(userId);
                
                // Initialize sample data for the new user
                DataInitializer.initializeData(userId);
                
                // Registration successful
                UIUtil.showInfo(this, "Account created successfully!");
                
                // Go to login screen
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
                this.dispose();
            } else {
                UIUtil.showError(this, "Registration failed");
            }
        } catch (Exception ex) {
            UIUtil.showError(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Action handler for the back button
     */
    private void backAction(ActionEvent e) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setVisible(true);
        this.dispose();
    }
    
    /**
     * Action handler for the login button
     */
    private void loginAction(ActionEvent e) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
} 