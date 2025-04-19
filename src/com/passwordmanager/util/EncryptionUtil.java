package com.passwordmanager.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for encryption and decryption operations
 * NOTE: This is for demo purposes only. In a real application, the key should be securely stored.
 */
public class EncryptionUtil {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    private static final String SECRET_KEY_ALGORITHM = "AES";
    
    // For demo purposes only - in a real application, this would be securely stored
    // This is a fixed key to ensure data persistence between application restarts
    private static final String FIXED_KEY = "jK8Hsp2QmAh7rZ5VdXw3gTbF9yN4pL6D";
    private static final SecretKey secretKey;
    
    static {
        // Convert the fixed key string to a SecretKey
        byte[] decodedKey = FIXED_KEY.getBytes(StandardCharsets.UTF_8);
        // Use only the first 32 bytes (256 bits) for AES-256
        byte[] keyBytes = new byte[32];
        System.arraycopy(decodedKey, 0, keyBytes, 0, Math.min(decodedKey.length, 32));
        secretKey = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
    }
    
    /**
     * Encrypts a plaintext string
     * 
     * @param plaintext The text to encrypt
     * @return Base64 encoded encrypted string with IV prepended
     */
    public static String encrypt(String plaintext) {
        try {
            // Generate a random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            
            // Initialize the cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            
            // Encrypt the data
            byte[] encryptedData = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            
            // Combine IV and encrypted data and encode to Base64
            byte[] combined = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);
            
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            System.err.println("Encryption error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Decrypts an encrypted string
     * 
     * @param encryptedText Base64 encoded encrypted string with IV prepended
     * @return The decrypted plaintext
     */
    public static String decrypt(String encryptedText) {
        try {
            // Decode from Base64
            byte[] combined = Base64.getDecoder().decode(encryptedText);
            
            // Extract IV and encrypted data
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encryptedData = new byte[combined.length - GCM_IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, encryptedData, 0, encryptedData.length);
            
            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            
            // Decrypt the data
            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Decryption error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
} 