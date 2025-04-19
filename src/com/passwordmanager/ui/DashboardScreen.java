package com.passwordmanager.ui;

import com.passwordmanager.dao.NoteDAO;
import com.passwordmanager.dao.PasswordDAO;
import com.passwordmanager.model.Note;
import com.passwordmanager.model.Password;
import com.passwordmanager.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Main dashboard screen for the Password Manager application
 */
public class DashboardScreen extends JFrame {
    
    private final User currentUser;
    private JTabbedPane tabbedPane;
    
    // Password components
    private JTable passwordTable;
    private DefaultTableModel passwordTableModel;
    private List<Password> passwordList;
    
    // Note components
    private JTable noteTable;
    private DefaultTableModel noteTableModel;
    private List<Note> noteList;
    
    /**
     * Constructor - Sets up the dashboard screen UI
     * 
     * @param user The authenticated user
     */
    public DashboardScreen(User user) {
        this.currentUser = user;
        
        // Set up the frame
        UIUtil.setupFrame(this, "Password & Notes Manager - Dashboard", 1200, 800);
        
        // Make the window full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Create the top panel for user info and logout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // User info label
        JLabel userLabel = new JLabel("Welcome, " + currentUser.getName());
        userLabel.setFont(UIUtil.LABEL_FONT);
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(userLabel, BorderLayout.WEST);
        
        // Logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        JButton logoutButton = UIUtil.createButton("Logout", this::logoutAction);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutPanel.add(logoutButton);
        topPanel.add(logoutPanel, BorderLayout.EAST);
        
        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // Create the tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UIUtil.LABEL_FONT);
        
        // Add the passwords tab
        JPanel passwordsPanel = createPasswordsPanel();
        tabbedPane.addTab("Passwords", passwordsPanel);
        
        // Add the notes tab
        JPanel notesPanel = createNotesPanel();
        tabbedPane.addTab("Notes", notesPanel);
        
        // Add the settings tab
        JPanel settingsPanel = createSettingsPanel();
        tabbedPane.addTab("Settings", settingsPanel);
        
        // Add the tabbed pane to the frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        // Load the data
        loadPasswordData();
        loadNoteData();
    }
    
    /**
     * Creates the passwords panel with password management UI
     * 
     * @return The configured panel
     */
    private JPanel createPasswordsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Create toolbar with action buttons
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.setOpaque(false);
        
        JButton addButton = UIUtil.createButton("Add Password", e -> showAddPasswordScreen());
        addButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(addButton);
        
        JButton viewButton = UIUtil.createButton("View Selected", e -> viewSelectedPassword());
        viewButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(viewButton);
        
        JButton deleteButton = UIUtil.createButton("Delete Selected", e -> deleteSelectedPassword());
        deleteButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(deleteButton);
        
        panel.add(toolbarPanel, BorderLayout.NORTH);
        
        // Create password table
        String[] columns = {"ID", "Title", "Username", "Website", "Last Updated"};
        passwordTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        passwordTable = new JTable(passwordTableModel);
        passwordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passwordTable.getColumnModel().getColumn(0).setMaxWidth(50);
        passwordTable.setFont(UIUtil.FIELD_FONT);
        passwordTable.setRowHeight(30);
        
        // Add double-click handler to view passwords
        passwordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewSelectedPassword();
                }
            }
        });
        
        // Hide the ID column
        passwordTable.getColumnModel().getColumn(0).setMinWidth(0);
        passwordTable.getColumnModel().getColumn(0).setMaxWidth(0);
        passwordTable.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane tableScrollPane = new JScrollPane(passwordTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Creates the notes panel with note management UI
     * 
     * @return The configured panel
     */
    private JPanel createNotesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Create toolbar with action buttons
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.setOpaque(false);
        
        JButton addButton = UIUtil.createButton("Add Note", e -> showAddNoteScreen());
        addButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(addButton);
        
        JButton viewButton = UIUtil.createButton("View Selected", e -> viewSelectedNote());
        viewButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(viewButton);
        
        JButton deleteButton = UIUtil.createButton("Delete Selected", e -> deleteSelectedNote());
        deleteButton.setPreferredSize(new Dimension(200, 40));
        toolbarPanel.add(deleteButton);
        
        panel.add(toolbarPanel, BorderLayout.NORTH);
        
        // Create note table
        String[] columns = {"ID", "Title", "Last Updated"};
        noteTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        noteTable = new JTable(noteTableModel);
        noteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        noteTable.getColumnModel().getColumn(0).setMaxWidth(50);
        noteTable.setFont(UIUtil.FIELD_FONT);
        noteTable.setRowHeight(30);
        
        // Add double-click handler to view notes
        noteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewSelectedNote();
                }
            }
        });
        
        // Hide the ID column
        noteTable.getColumnModel().getColumn(0).setMinWidth(0);
        noteTable.getColumnModel().getColumn(0).setMaxWidth(0);
        noteTable.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane tableScrollPane = new JScrollPane(noteTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Creates the settings panel with user settings UI
     * 
     * @return The configured panel
     */
    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Profile section
        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile"));
        
        // Add profile fields - name, email, phone
        JLabel nameLabel = new JLabel("Name: " + currentUser.getName());
        nameLabel.setFont(UIUtil.LABEL_FONT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel emailLabel = new JLabel("Email: " + currentUser.getEmail());
        emailLabel.setFont(UIUtil.LABEL_FONT);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel phoneLabel = new JLabel("Phone: " + currentUser.getPhone());
        phoneLabel.setFont(UIUtil.LABEL_FONT);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        profilePanel.add(nameLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(emailLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(phoneLabel);
        
        // Button to edit profile
        JButton editProfileButton = UIUtil.createButton("Edit Profile", e -> showEditProfileScreen());
        editProfileButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(Box.createVerticalStrut(20));
        profilePanel.add(editProfileButton);
        
        panel.add(profilePanel);
        panel.add(Box.createVerticalStrut(30));
        
        // Security section
        JPanel securityPanel = new JPanel();
        securityPanel.setOpaque(false);
        securityPanel.setLayout(new BoxLayout(securityPanel, BoxLayout.Y_AXIS));
        securityPanel.setBorder(BorderFactory.createTitledBorder("Security"));
        
        // Button to change password
        JButton changePasswordButton = UIUtil.createButton("Change Password", e -> showChangePasswordScreen());
        changePasswordButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        securityPanel.add(changePasswordButton);
        
        panel.add(securityPanel);
        
        return panel;
    }
    
    /**
     * Loads password data from the database
     */
    private void loadPasswordData() {
        PasswordDAO passwordDAO = new PasswordDAO();
        passwordList = passwordDAO.getAllPasswordsByUserId(currentUser.getId());
        
        // Clear the table
        passwordTableModel.setRowCount(0);
        
        // Add the passwords to the table
        for (Password password : passwordList) {
            Object[] row = {
                password.getId(),
                password.getTitle(),
                password.getUsername(),
                password.getWebsite(),
                password.getLastUpdated()
            };
            passwordTableModel.addRow(row);
        }
    }
    
    /**
     * Loads note data from the database
     */
    private void loadNoteData() {
        NoteDAO noteDAO = new NoteDAO();
        noteList = noteDAO.getAllNotesByUserId(currentUser.getId());
        
        // Clear the table
        noteTableModel.setRowCount(0);
        
        // Add the notes to the table
        for (Note note : noteList) {
            Object[] row = {
                note.getId(),
                note.getTitle(),
                note.getLastUpdated()
            };
            noteTableModel.addRow(row);
        }
    }
    
    /**
     * Shows the add password screen
     */
    private void showAddPasswordScreen() {
        PasswordDialog dialog = new PasswordDialog(this, currentUser.getId());
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Password newPassword = dialog.getPassword();
            PasswordDAO passwordDAO = new PasswordDAO();
            
            int passwordId = passwordDAO.createPassword(newPassword);
            if (passwordId > 0) {
                UIUtil.showInfo(this, "Password added successfully!");
                loadPasswordData();
            } else {
                UIUtil.showError(this, "Failed to add password");
            }
        }
    }
    
    /**
     * Views the selected password
     */
    private void viewSelectedPassword() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow == -1) {
            UIUtil.showError(this, "Please select a password to view");
            return;
        }
        
        int passwordId = (int) passwordTable.getValueAt(selectedRow, 0);
        Password password = null;
        
        // Find the password in the list
        for (Password p : passwordList) {
            if (p.getId() == passwordId) {
                password = p;
                break;
            }
        }
        
        if (password != null) {
            PasswordDialog dialog = new PasswordDialog(this, password);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                Password updatedPassword = dialog.getPassword();
                PasswordDAO passwordDAO = new PasswordDAO();
                
                if (passwordDAO.updatePassword(updatedPassword)) {
                    UIUtil.showInfo(this, "Password updated successfully!");
                    loadPasswordData();
                } else {
                    UIUtil.showError(this, "Failed to update password");
                }
            }
        }
    }
    
    /**
     * Deletes the selected password
     */
    private void deleteSelectedPassword() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow == -1) {
            UIUtil.showError(this, "Please select a password to delete");
            return;
        }
        
        int passwordId = (int) passwordTable.getValueAt(selectedRow, 0);
        String passwordTitle = (String) passwordTable.getValueAt(selectedRow, 1);
        
        boolean confirm = UIUtil.showConfirmation(this, "Are you sure you want to delete the password '" + passwordTitle + "'?");
        if (confirm) {
            PasswordDAO passwordDAO = new PasswordDAO();
            
            if (passwordDAO.deletePassword(passwordId)) {
                UIUtil.showInfo(this, "Password deleted successfully!");
                loadPasswordData();
            } else {
                UIUtil.showError(this, "Failed to delete password");
            }
        }
    }
    
    /**
     * Shows the add note screen
     */
    private void showAddNoteScreen() {
        NoteDialog dialog = new NoteDialog(this, currentUser.getId());
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Note newNote = dialog.getNote();
            NoteDAO noteDAO = new NoteDAO();
            
            int noteId = noteDAO.createNote(newNote);
            if (noteId > 0) {
                UIUtil.showInfo(this, "Note added successfully!");
                loadNoteData();
            } else {
                UIUtil.showError(this, "Failed to add note");
            }
        }
    }
    
    /**
     * Views the selected note
     */
    private void viewSelectedNote() {
        int selectedRow = noteTable.getSelectedRow();
        if (selectedRow == -1) {
            UIUtil.showError(this, "Please select a note to view");
            return;
        }
        
        int noteId = (int) noteTable.getValueAt(selectedRow, 0);
        Note note = null;
        
        // Find the note in the list
        for (Note n : noteList) {
            if (n.getId() == noteId) {
                note = n;
                break;
            }
        }
        
        if (note != null) {
            NoteDialog dialog = new NoteDialog(this, note);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                Note updatedNote = dialog.getNote();
                NoteDAO noteDAO = new NoteDAO();
                
                if (noteDAO.updateNote(updatedNote)) {
                    UIUtil.showInfo(this, "Note updated successfully!");
                    loadNoteData();
                } else {
                    UIUtil.showError(this, "Failed to update note");
                }
            }
        }
    }
    
    /**
     * Deletes the selected note
     */
    private void deleteSelectedNote() {
        int selectedRow = noteTable.getSelectedRow();
        if (selectedRow == -1) {
            UIUtil.showError(this, "Please select a note to delete");
            return;
        }
        
        int noteId = (int) noteTable.getValueAt(selectedRow, 0);
        String noteTitle = (String) noteTable.getValueAt(selectedRow, 1);
        
        boolean confirm = UIUtil.showConfirmation(this, "Are you sure you want to delete the note '" + noteTitle + "'?");
        if (confirm) {
            NoteDAO noteDAO = new NoteDAO();
            
            if (noteDAO.deleteNote(noteId)) {
                UIUtil.showInfo(this, "Note deleted successfully!");
                loadNoteData();
            } else {
                UIUtil.showError(this, "Failed to delete note");
            }
        }
    }
    
    /**
     * Shows the edit profile screen
     */
    private void showEditProfileScreen() {
        // To be implemented
        UIUtil.showInfo(this, "Edit Profile coming soon");
    }
    
    /**
     * Shows the change password screen
     */
    private void showChangePasswordScreen() {
        // To be implemented
        UIUtil.showInfo(this, "Change Password coming soon");
    }
    
    /**
     * Handles the logout action
     */
    private void logoutAction(ActionEvent e) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
} 