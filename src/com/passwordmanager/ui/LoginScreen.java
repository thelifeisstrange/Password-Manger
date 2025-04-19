package com.passwordmanager.ui;

import com.passwordmanager.dao.UserDAO;
import com.passwordmanager.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;

/**
 * Login screen for user authentication
 */
public class LoginScreen extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    
    /**
     * Constructor - Sets up the login screen UI
     */
    public LoginScreen() {
        // Set up the frame
        UIUtil.setupFrame(this, "SecureVault - Login", 600, 550);
        
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
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(UIUtil.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Create main card panel for login form with responsive sizing
        JPanel cardPanel = UIUtil.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 40, 40, 40));
        
        // Add login icon as emoji
        JLabel iconLabel = new JLabel("🔒", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 72));
        iconLabel.setForeground(UIUtil.PRIMARY_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        
        // Create responsive form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Dynamically adjust form width based on parent container
        formPanel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && formPanel.isShowing()) {
                Container parent = formPanel.getParent();
                if (parent != null) {
                    int width = Math.max(200, parent.getWidth() - 80);  // Subtract padding
                    formPanel.setMaximumSize(new Dimension(width, 500));
                    formPanel.revalidate();
                }
            }
        });
        
        // Add email field with placeholder
        emailField = UIUtil.createTextField("Enter email");
        JPanel emailPanel = UIUtil.createFormRow("Email", emailField);
        formPanel.add(emailPanel);
        
        // Add some space
        formPanel.add(Box.createVerticalStrut(20));
        
        // Add password field with placeholder
        passwordField = UIUtil.createPasswordField("Enter password");
        JPanel passwordPanel = UIUtil.createFormRow("Password", passwordField);
        formPanel.add(passwordPanel);
        
        cardPanel.add(formPanel);
        
        // Add forgot password link
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        forgotPasswordButton.setForeground(UIUtil.PRIMARY_COLOR);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.addActionListener(this::forgotPasswordAction);
        
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(forgotPasswordButton);
        cardPanel.add(Box.createVerticalStrut(30));
        
        // Add the login button
        JButton loginButton = UIUtil.createButton("Login", this::loginAction);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Make login button width responsive
        loginButton.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && loginButton.isShowing()) {
                Container parent = loginButton.getParent();
                if (parent != null) {
                    int width = Math.min(200, Math.max(120, parent.getWidth() / 2));
                    loginButton.setMaximumSize(new Dimension(width, UIUtil.BUTTON_SIZE.height));
                    loginButton.revalidate();
                }
            }
        });
        
        cardPanel.add(loginButton);
        
        // Add register prompt
        JPanel registerPromptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPromptPanel.setOpaque(false);
        registerPromptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel registerLabel = new JLabel("No account?");
        registerLabel.setFont(UIUtil.SMALL_FONT);
        registerLabel.setForeground(UIUtil.SECONDARY_TEXT_COLOR);
        
        JButton registerButton = new JButton("Sign up");
        registerButton.setFont(UIUtil.SMALL_FONT);
        registerButton.setForeground(UIUtil.PRIMARY_COLOR);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(this::signupAction);
        
        registerPromptPanel.add(registerLabel);
        registerPromptPanel.add(registerButton);
        
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(registerPromptPanel);
        
        // Create responsive wrapper panel for automatic centering and sizing
        JPanel centerPanel = UIUtil.createResponsivePanel(cardPanel);
        
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        
        // Set minimum size for the frame
        setMinimumSize(new Dimension(350, 500));
    }
    
    /**
     * Action handler for the login button
     */
    private void loginAction(ActionEvent e) {
        // Get the user input
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            UIUtil.showError(this, "Enter email and password");
            return;
        }
        
        // Authenticate the user
        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.authenticateUser(email, password);
            
            if (user != null) {
                // Login successful - open the main dashboard
                DashboardScreen dashboard = new DashboardScreen(user);
                dashboard.setVisible(true);
                this.dispose();
            } else {
                // Login failed
                UIUtil.showError(this, "Invalid login");
                passwordField.setText("");
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
     * Action handler for the signup button
     */
    private void signupAction(ActionEvent e) {
        SignupScreen signupScreen = new SignupScreen();
        signupScreen.setVisible(true);
        this.dispose();
    }
    
    /**
     * Action handler for the forgot password button
     */
    private void forgotPasswordAction(ActionEvent e) {
        ForgotPasswordScreen forgotPasswordScreen = new ForgotPasswordScreen();
        forgotPasswordScreen.setVisible(true);
        this.dispose();
    }
} 