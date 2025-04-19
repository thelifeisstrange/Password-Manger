package com.passwordmanager.ui;

import com.passwordmanager.dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.util.Random;

/**
 * Forgot Password screen for resetting user passwords
 */
public class ForgotPasswordScreen extends JFrame {
    
    private JTextField emailField;
    private JPanel mainPanel;
    private JPanel resetPanel;
    private JPanel successPanel;
    private JTextField verificationCodeField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    
    private String generatedCode;
    private String userEmail;
    
    /**
     * Constructor - Sets up the forgot password screen UI
     */
    public ForgotPasswordScreen() {
        // Set up the frame
        UIUtil.setupFrame(this, "SecureVault - Reset Password", 600, 550);
        
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
        JLabel titleLabel = new JLabel("Reset Password", SwingConstants.CENTER);
        titleLabel.setFont(UIUtil.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Create card layout to switch between different panels
        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);
        
        // Create email panel
        JPanel emailPanel = createEmailPanel();
        
        // Create reset panel
        resetPanel = createResetPanel();
        
        // Create success panel
        successPanel = createSuccessPanel();
        
        // Add panels to card layout
        mainPanel.add(emailPanel, "EMAIL");
        mainPanel.add(resetPanel, "RESET");
        mainPanel.add(successPanel, "SUCCESS");
        
        // Set initial panel
        cardLayout.show(mainPanel, "EMAIL");
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        
        getContentPane().add(wrapperPanel, BorderLayout.CENTER);
        
        // Set minimum size
        setMinimumSize(new Dimension(350, 500));
    }
    
    /**
     * Creates the email panel for user to enter their email
     */
    private JPanel createEmailPanel() {
        JPanel cardPanel = UIUtil.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 40, 40, 40));
        
        // Add icon
        JLabel iconLabel = new JLabel("🔑", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setForeground(UIUtil.PRIMARY_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconLabel);
        cardPanel.add(Box.createVerticalStrut(15));
        
        // Add instructions
        JLabel instructionsLabel = new JLabel("Enter your email to reset password");
        instructionsLabel.setFont(UIUtil.SUBTITLE_FONT);
        instructionsLabel.setForeground(UIUtil.TEXT_COLOR);
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(instructionsLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        
        // Add email field
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Responsive form width
        formPanel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && formPanel.isShowing()) {
                Container parent = formPanel.getParent();
                if (parent != null) {
                    int width = Math.max(200, parent.getWidth() - 80);
                    formPanel.setMaximumSize(new Dimension(width, 100));
                    formPanel.revalidate();
                }
            }
        });
        
        emailField = UIUtil.createTextField("Enter email");
        JPanel emailInputPanel = UIUtil.createFormRow("Email", emailField);
        formPanel.add(emailInputPanel);
        
        cardPanel.add(formPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        
        // Add continue button
        JButton continueButton = UIUtil.createButton("Continue", this::continueAction);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Responsive button width
        continueButton.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && continueButton.isShowing()) {
                Container parent = continueButton.getParent();
                if (parent != null) {
                    int width = Math.min(200, Math.max(120, parent.getWidth() / 2));
                    continueButton.setMaximumSize(new Dimension(width, UIUtil.BUTTON_SIZE.height));
                    continueButton.revalidate();
                }
            }
        });
        
        cardPanel.add(continueButton);
        
        // Wrap in responsive panel
        return UIUtil.createResponsivePanel(cardPanel);
    }
    
    /**
     * Creates the reset panel for verification and new password
     */
    private JPanel createResetPanel() {
        JPanel cardPanel = UIUtil.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(20, 40, 30, 40));
        
        // Add title
        JLabel resetTitle = new JLabel("Reset Your Password");
        resetTitle.setFont(UIUtil.SUBTITLE_FONT);
        resetTitle.setForeground(UIUtil.TEXT_COLOR);
        resetTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(resetTitle);
        cardPanel.add(Box.createVerticalStrut(20));
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Responsive form width
        formPanel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && formPanel.isShowing()) {
                Container parent = formPanel.getParent();
                if (parent != null) {
                    int width = Math.max(200, parent.getWidth() - 80);
                    formPanel.setMaximumSize(new Dimension(width, 300));
                    formPanel.revalidate();
                }
            }
        });
        
        // Verification code field
        verificationCodeField = UIUtil.createTextField("Enter verification code");
        JPanel codePanel = UIUtil.createFormRow("Verification Code", verificationCodeField);
        formPanel.add(codePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // New password field
        newPasswordField = UIUtil.createPasswordField("Enter new password");
        JPanel newPasswordPanel = UIUtil.createFormRow("New Password", newPasswordField);
        formPanel.add(newPasswordPanel);
        
        // Add password requirements note
        JLabel passwordRequirements = new JLabel("<html>Min 8 chars with letters, numbers, symbols</html>");
        passwordRequirements.setFont(UIUtil.SMALL_FONT);
        passwordRequirements.setForeground(UIUtil.SECONDARY_TEXT_COLOR);
        passwordRequirements.setBorder(new EmptyBorder(3, 0, 3, 0));
        passwordRequirements.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordRequirements);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Confirm password field
        confirmPasswordField = UIUtil.createPasswordField("Confirm new password");
        JPanel confirmPasswordPanel = UIUtil.createFormRow("Confirm Password", confirmPasswordField);
        formPanel.add(confirmPasswordPanel);
        
        cardPanel.add(formPanel);
        cardPanel.add(Box.createVerticalStrut(20));
        
        // Add reset button
        JButton resetButton = UIUtil.createButton("Reset Password", this::resetPasswordAction);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Responsive button width
        resetButton.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && resetButton.isShowing()) {
                Container parent = resetButton.getParent();
                if (parent != null) {
                    int width = Math.min(200, Math.max(150, parent.getWidth() / 2));
                    resetButton.setMaximumSize(new Dimension(width, UIUtil.BUTTON_SIZE.height));
                    resetButton.revalidate();
                }
            }
        });
        
        cardPanel.add(resetButton);
        
        // Wrap in responsive panel
        return UIUtil.createResponsivePanel(cardPanel);
    }
    
    /**
     * Creates the success panel to show after password reset
     */
    private JPanel createSuccessPanel() {
        JPanel cardPanel = UIUtil.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 40, 40, 40));
        
        // Add success icon
        JLabel iconLabel = new JLabel("✅", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setForeground(UIUtil.SUCCESS_COLOR);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        
        // Add success message
        JLabel successLabel = new JLabel("Password Reset Successfully!");
        successLabel.setFont(UIUtil.SUBTITLE_FONT);
        successLabel.setForeground(UIUtil.TEXT_COLOR);
        successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(successLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        
        JLabel instructionLabel = new JLabel("You can now login with your new password");
        instructionLabel.setFont(UIUtil.FIELD_FONT);
        instructionLabel.setForeground(UIUtil.SECONDARY_TEXT_COLOR);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(instructionLabel);
        cardPanel.add(Box.createVerticalStrut(30));
        
        // Add login button
        JButton loginButton = UIUtil.createButton("Go to Login", this::goToLoginAction);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Responsive button width
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
        
        // Wrap in responsive panel
        return UIUtil.createResponsivePanel(cardPanel);
    }
    
    /**
     * Action handler for the back button
     */
    private void backAction(ActionEvent e) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
    
    /**
     * Action handler for the continue button
     */
    private void continueAction(ActionEvent e) {
        String email = emailField.getText().trim();
        
        // Validate email
        if (email.isEmpty()) {
            UIUtil.showError(this, "Enter email");
            return;
        }
        
        // Check if email exists in the database
        UserDAO userDAO = new UserDAO();
        if (!userDAO.emailExists(email)) {
            UIUtil.showError(this, "Email not found");
            return;
        }
        
        // Store email for later use
        this.userEmail = email;
        
        // Generate verification code (6 digits)
        Random random = new Random();
        this.generatedCode = String.format("%06d", random.nextInt(1000000));
        
        // In a real app, we would send this code to the user's email
        // For this demo, we'll show it in a dialog
        UIUtil.showInfo(this, "Verification code: " + generatedCode + "\n\nIn a real app, this would be sent to your email.");
        
        // Show reset panel
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "RESET");
    }
    
    /**
     * Action handler for the reset password button
     */
    private void resetPasswordAction(ActionEvent e) {
        String code = verificationCodeField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate inputs
        if (code.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            UIUtil.showError(this, "Fill all fields");
            return;
        }
        
        // Verify code
        if (!code.equals(generatedCode)) {
            UIUtil.showError(this, "Invalid code");
            return;
        }
        
        // Verify passwords match
        if (!newPassword.equals(confirmPassword)) {
            UIUtil.showError(this, "Passwords don't match");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        // Validate password strength
        if (newPassword.length() < 8) {
            UIUtil.showError(this, "Password too short");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            return;
        }
        
        // Reset the password
        UserDAO userDAO = new UserDAO();
        if (userDAO.resetPasswordByEmail(userEmail, newPassword)) {
            // Show success panel
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "SUCCESS");
        } else {
            UIUtil.showError(this, "Failed to reset password");
        }
    }
    
    /**
     * Action handler for the go to login button
     */
    private void goToLoginAction(ActionEvent e) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
} 