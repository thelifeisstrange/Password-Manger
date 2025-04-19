-- Drop database if exists and create it
DROP DATABASE IF EXISTS password_manager;
CREATE DATABASE password_manager;
USE password_manager;

-- Create user table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create password_entries table
CREATE TABLE password_entries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    website VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    notes TEXT,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create notes table
CREATE TABLE notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_password_entries_user_id ON password_entries(user_id);
CREATE INDEX idx_password_entries_website ON password_entries(website);
CREATE INDEX idx_password_entries_category ON password_entries(category);
CREATE INDEX idx_notes_user_id ON notes(user_id);
CREATE INDEX idx_notes_title ON notes(title);
CREATE INDEX idx_notes_category ON notes(category); 