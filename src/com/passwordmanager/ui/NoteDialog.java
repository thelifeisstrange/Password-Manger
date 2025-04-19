package com.passwordmanager.ui;

import com.passwordmanager.model.Note;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Dialog for adding or editing a note
 */
public class NoteDialog extends JDialog {
    
    private JTextField titleField;
    private JTextArea contentArea;
    private boolean confirmed = false;
    private Note note;
    
    /**
     * Constructor for creating a new note
     * 
     * @param parent The parent frame
     * @param userId The user ID to associate the note with
     */
    public NoteDialog(JFrame parent, int userId) {
        this(parent, new Note());
        this.note.setUserId(userId);
    }
    
    /**
     * Constructor for editing an existing note
     * 
     * @param parent The parent frame
     * @param note The note to edit
     */
    public NoteDialog(JFrame parent, Note note) {
        super(parent, true);
        this.note = note;
        
        // Set up the dialog
        setTitle(note.getId() > 0 ? "Edit Note" : "Add Note");
        setSize(600, 550);
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
        
        // Add the content field
        JLabel contentLabel = new JLabel("Content");
        contentLabel.setFont(UIUtil.LABEL_FONT);
        contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(contentLabel);
        
        contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(UIUtil.FIELD_FONT);
        
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setPreferredSize(new Dimension(550, 300));
        contentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(contentScrollPane);
        
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
        
        // Populate fields if editing an existing note
        if (note.getId() > 0) {
            titleField.setText(note.getTitle());
            contentArea.setText(note.getContent());
        }
    }
    
    /**
     * Handles the save action
     */
    private void saveAction(ActionEvent e) {
        // Validate inputs
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        
        if (title.isEmpty()) {
            UIUtil.showError(this, "Please enter a title");
            return;
        }
        
        // Update the note object
        this.note.setTitle(title);
        this.note.setContent(content);
        
        confirmed = true;
        dispose();
    }
    
    /**
     * Returns the note object
     */
    public Note getNote() {
        return note;
    }
    
    /**
     * Returns true if the user confirmed the dialog
     */
    public boolean isConfirmed() {
        return confirmed;
    }
} 