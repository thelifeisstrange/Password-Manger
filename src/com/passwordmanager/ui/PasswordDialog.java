package com.passwordmanager.ui;

import com.passwordmanager.model.Password;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Dialog for adding or editing a password
 */
public class PasswordDialog extends JDialog {
    
    private JTextField titleField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField websiteField;
    private JTextArea notesArea;
    private boolean confirmed = false;
    private Password password;
    
    /**
     * Constructor for creating a new password
     * 
     * @param parent The parent frame
     * @param userId The user ID to associate the password with
     */
    public PasswordDialog(JFrame parent, int userId) {
        this(parent, new Password());
        this.password.setUserId(userId);
    }
    
    /**
     * Constructor for editing an existing password
     * 
     * @param parent The parent frame
     * @param password The password to edit
     */
    public PasswordDialog(JFrame parent, Password password) {
        super(parent, true);
        this.password = password;
        
        // Set up the dialog
        setTitle(password.getId() > 0 ? "Edit Password" : "Add Password");
        setSize(550, 520);
        setLocationRelativeTo(parent);
        setResizable(true);
        
        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // Add the title field
        titleField = UIUtil.createTextField("");
        JPanel titlePanel = UIUtil.createFormRow("Title", titleField);
        formPanel.add(titlePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add the username field
        usernameField = UIUtil.createTextField("");
        JPanel usernamePanel = UIUtil.createFormRow("Username", usernameField);
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add the password field
        JPanel passwordPanel = new JPanel(new BorderLayout(10, 0));
        passwordPanel.setOpaque(false);
        
        passwordField = UIUtil.createPasswordField("");
        passwordField.setPreferredSize(new Dimension(350, 40));
        
        JButton generateButton = new JButton("Generate");
        generateButton.setFont(UIUtil.SMALL_FONT);
        generateButton.addActionListener(this::generatePassword);
        
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(generateButton, BorderLayout.EAST);
        
        JPanel passwordWrapper = UIUtil.createFormRow("Password", passwordPanel);
        formPanel.add(passwordWrapper);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add the website field
        websiteField = UIUtil.createTextField("");
        JPanel websitePanel = UIUtil.createFormRow("Website", websiteField);
        formPanel.add(websitePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Add the notes field
        notesArea = new JTextArea();
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setFont(UIUtil.FIELD_FONT);
        
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setPreferredSize(new Dimension(400, 120));
        
        JPanel notesPanel = UIUtil.createFormRow("Notes", notesScrollPane);
        formPanel.add(notesPanel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton saveButton = UIUtil.createButton("Save", this::saveAction);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(UIUtil.BUTTON_FONT);
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add the main panel to the dialog
        getContentPane().add(mainPanel);
        
        // Populate fields if editing an existing password
        if (password.getId() > 0) {
            titleField.setText(password.getTitle());
            usernameField.setText(password.getUsername());
            passwordField.setText(password.getPassword());
            websiteField.setText(password.getWebsite());
            notesArea.setText(password.getNotes());
        }
    }
    
    /**
     * Generates a random secure password
     */
    private void generatePassword(ActionEvent e) {
        // Generate a secure random password with letters, numbers, and symbols
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        
        // Generate a password of length 16
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        passwordField.setText(sb.toString());
    }
    
    /**
     * Handles the save action
     */
    private void saveAction(ActionEvent e) {
        // Validate inputs
        String title = titleField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String website = websiteField.getText().trim();
        String notes = notesArea.getText().trim();
        
        if (title.isEmpty()) {
            UIUtil.showError(this, "Please enter a title");
            return;
        }
        
        // Update the password object
        this.password.setTitle(title);
        this.password.setUsername(username);
        this.password.setPassword(password);
        this.password.setWebsite(website);
        this.password.setNotes(notes);
        
        confirmed = true;
        dispose();
    }
    
    /**
     * Returns the password object
     */
    public Password getPassword() {
        return password;
    }
    
    /**
     * Returns true if the user confirmed the dialog
     */
    public boolean isConfirmed() {
        return confirmed;
    }
} 