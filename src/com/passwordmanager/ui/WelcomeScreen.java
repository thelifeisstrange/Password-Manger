package com.passwordmanager.ui;

import com.passwordmanager.util.DatabaseUtil;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;

/**
 * Welcome screen for the Password Manager application
 */
public class WelcomeScreen extends JFrame {
    
    /**
     * Constructor - Sets up the welcome screen UI
     */
    public WelcomeScreen() {
        // Set up the frame with gradient background
        UIUtil.setupFrame(this, "SecureVault", 800, 550);
        
        // Make the window full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Create main content panel with card-like appearance
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        // Left panel for app description
        JPanel leftPanel = createInfoPanel();
        contentPanel.add(leftPanel, BorderLayout.WEST);
        
        // Right panel for login/signup
        JPanel rightPanel = createLoginPanel();
        contentPanel.add(rightPanel, BorderLayout.CENTER);
        
        // Add footer panel
        JPanel footerPanel = createFooterPanel();
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
        
        // Make sure everything is visible
        setVisible(true);
    }
    
    /**
     * Creates the info panel with app description
     */
    private JPanel createInfoPanel() {
        JPanel panel = UIUtil.createCardPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(330, 350));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Add logo using emoji instead of image resource
        JLabel iconLabel = new JLabel("🔐", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(5));
        
        // Add app name
        JLabel appNameLabel = new JLabel("SecureVault");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        appNameLabel.setForeground(UIUtil.PRIMARY_COLOR);
        appNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(appNameLabel);
        panel.add(Box.createVerticalStrut(5));
        
        // Add app description with better text
        String[] features = {
            "✓ AES-256 encryption",
            "✓ Organize by category",
            "✓ Secure note storage",
            "✓ Generate passwords",
        };
        
        // Create panel for features
        JPanel featuresPanel = new JPanel();
        featuresPanel.setOpaque(false);
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(UIUtil.LABEL_FONT);
            featureLabel.setForeground(UIUtil.TEXT_COLOR);
            featureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            featureLabel.setBorder(new EmptyBorder(2, 0, 2, 0));
            featuresPanel.add(featureLabel);
        }
        
        panel.add(featuresPanel);
        
        return panel;
    }
    
    /**
     * Creates the login panel with login/signup buttons
     */
    private JPanel createLoginPanel() {
        JPanel panel = UIUtil.createCardPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Add title
        JLabel titleLabel = UIUtil.createTitleLabel("Welcome");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        panel.add(titleLabel);
        
        // Add subtitle
        JLabel subtitleLabel = new JLabel("Secure password manager");
        subtitleLabel.setFont(UIUtil.LABEL_FONT);
        subtitleLabel.setForeground(UIUtil.TEXT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Add login button
        JButton loginButton = UIUtil.createButton("Login", this::loginAction);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(200, UIUtil.BUTTON_SIZE.height));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        
        // Add signup button
        JButton signupButton = UIUtil.createButton("Create Account", this::signupAction);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setMaximumSize(new Dimension(200, UIUtil.BUTTON_SIZE.height));
        panel.add(signupButton);
        panel.add(Box.createVerticalStrut(15));
        
        // Add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(UIUtil.BUTTON_FONT);
        exitButton.setForeground(UIUtil.TEXT_COLOR);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(this::exitAction);
        panel.add(exitButton);
        
        return panel;
    }
    
    /**
     * Creates the footer panel
     */
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        
        JLabel copyrightLabel = new JLabel("<html>© 2022 SecureVault | Developed By <a href=''>Yogesh</a></html>");
        copyrightLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyrightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://yogesh-kulkarni.web.app"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        copyrightLabel.setForeground(Color.WHITE);
        copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(copyrightLabel);
        
        return panel;
    }
    
    /**
     * Action handler for the login button
     */
    private void loginAction(ActionEvent e) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
    
    /**
     * Action handler for the sign up button
     */
    private void signupAction(ActionEvent e) {
        SignupScreen signupScreen = new SignupScreen();
        signupScreen.setVisible(true);
        this.dispose();
    }
    
    /**
     * Action handler for the exit button
     */
    private void exitAction(ActionEvent e) {
        if (UIUtil.showConfirmation(this, "Exit app?")) {
            System.exit(0);
        }
    }
} 