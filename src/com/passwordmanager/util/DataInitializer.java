package com.passwordmanager.util;

import com.passwordmanager.dao.NoteDAO;
import com.passwordmanager.dao.PasswordDAO;
import com.passwordmanager.model.Note;
import com.passwordmanager.model.Password;

/**
 * Utility class to initialize sample data for new users
 */
public class DataInitializer {
    
    /**
     * Adds sample passwords for a new user
     * 
     * @param userId The ID of the user to add passwords for
     */
    public static void addSamplePasswords(int userId) {
        PasswordDAO passwordDAO = new PasswordDAO();
        
        // Sample email password
        Password emailPassword = new Password();
        emailPassword.setUserId(userId);
        emailPassword.setTitle("Gmail Account");
        emailPassword.setUsername("myemail@gmail.com");
        emailPassword.setPassword("SamplePass123!");
        emailPassword.setWebsite("https://gmail.com");
        emailPassword.setNotes("My primary email account");
        passwordDAO.createPassword(emailPassword);
        
        // Sample social media password
        Password socialPassword = new Password();
        socialPassword.setUserId(userId);
        socialPassword.setTitle("Facebook");
        socialPassword.setUsername("user.name");
        socialPassword.setPassword("Fb@sample456");
        socialPassword.setWebsite("https://facebook.com");
        socialPassword.setNotes("Personal Facebook account");
        passwordDAO.createPassword(socialPassword);
        
        // Sample banking password
        Password bankPassword = new Password();
        bankPassword.setUserId(userId);
        bankPassword.setTitle("Online Banking");
        bankPassword.setUsername("bankuser");
        bankPassword.setPassword("B@nk!Secure789");
        bankPassword.setWebsite("https://mybank.com");
        bankPassword.setNotes("Online banking portal - change password monthly");
        passwordDAO.createPassword(bankPassword);
    }
    
    /**
     * Adds sample notes for a new user
     * 
     * @param userId The ID of the user to add notes for
     */
    public static void addSampleNotes(int userId) {
        NoteDAO noteDAO = new NoteDAO();
        
        // Welcome note
        Note welcomeNote = new Note();
        welcomeNote.setUserId(userId);
        welcomeNote.setTitle("Welcome to SecureVault");
        welcomeNote.setContent("Welcome to SecureVault Password Manager!\n\n" +
                "This is a sample note to help you get started. You can create, edit, and delete notes " +
                "from the Notes tab in the dashboard.\n\n" +
                "Notes are securely stored and can be used to keep important information like:\n" +
                "- Recovery codes\n" +
                "- Security questions and answers\n" +
                "- Important numbers\n" +
                "- Any information you want to keep secure\n\n" +
                "Enjoy using SecureVault!");
        noteDAO.createNote(welcomeNote);
        
        // Recovery codes note
        Note recoveryNote = new Note();
        recoveryNote.setUserId(userId);
        recoveryNote.setTitle("Backup Recovery Codes");
        recoveryNote.setContent("Store your backup/recovery codes here:\n\n" +
                "Gmail: ABCD-EFGH-IJKL-MNOP\n" +
                "Microsoft: 1234-5678-9012-3456\n" +
                "Apple ID: QWERTY-ASDFGH-ZXCVBN\n\n" +
                "Remember to update this note whenever you generate new recovery codes!");
        noteDAO.createNote(recoveryNote);
    }
    
    /**
     * Initializes all sample data for a new user
     * 
     * @param userId The ID of the user to add data for
     */
    public static void initializeData(int userId) {
        addSamplePasswords(userId);
        addSampleNotes(userId);
    }
} 