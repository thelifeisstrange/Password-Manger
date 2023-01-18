import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Signup {
    Signup() {
        // Create the frame
        JFrame frame = new JFrame("Signup");
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 550);

        // Create the main panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, new Color(66, 139, 202), 0, getHeight(), new Color(0, 128, 128));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Create the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(titlePanel);

        // Add the title label
        JLabel titleLabel = new JLabel("Signup for a new account");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        // Create the form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(255, 255, 255, 200));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(formPanel);
        // Add the name label and field
        JTextField nameField = new JTextField(20);
        nameField.setBackground(new Color(255, 255, 255, 100));
        nameField.setForeground(Color.DARK_GRAY);
        nameField.setFont(nameField.getFont().deriveFont(14f));
        nameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(createFormRow("Name:", nameField));

        // Add the email label and field
        JTextField emailField = new JTextField(20);
        emailField.setBackground(new Color(255, 255, 255, 100));
        emailField.setForeground(Color.DARK_GRAY);
        emailField.setFont(emailField.getFont().deriveFont(14f));
        emailField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(createFormRow("Email:", emailField));

        // Add the phone number label and field
        JTextField phoneField = new JTextField(20);
        phoneField.setBackground(new Color(255, 255, 255, 100));
        phoneField.setForeground(Color.DARK_GRAY);
        phoneField.setFont(phoneField.getFont().deriveFont(14f));
        phoneField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(createFormRow("Phone Number:", phoneField));

        // Add the password label and field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(255, 255, 255, 100));
        passwordField.setForeground(Color.DARK_GRAY);
        passwordField.setFont(passwordField.getFont().deriveFont(14f));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(createFormRow("Password:", passwordField));

        // Add the confirm password label and field
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBackground(new Color(255, 255, 255, 100));
        confirmPasswordField.setForeground(Color.DARK_GRAY);
        confirmPasswordField.setFont(confirmPasswordField.getFont().deriveFont(14f));
        confirmPasswordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(createFormRow("Confirm Password:", confirmPasswordField));

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255, 0));
        mainPanel.add(buttonPanel);

        // Add the signup button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(150, 50));
        signupButton.setBackground(new Color(66, 139, 202));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(signupButton.getFont().deriveFont(18f));
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

    		    // Get the data from the text fields
//    		    String phoneNumber = ((JTextField)formPanel.getComponent(0)).getText();
//    		    String vehicleNumber = ((JTextField)formPanel.getComponent(1)).getText();
//    		    String timeIn = ((JTextField)formPanel.getComponent(2)).getText();
            	String name = nameField.getText().toString();
            	String email = emailField.getText().toString();
            	String phone = phoneField.getText().toString();
            	String pass = passwordField.getPassword().toString();

    		    // Connect to the database
    		    Connection connection = null;
    		    try {
        		   Class.forName("com.mysql.cj.jdbc.Driver"); 
    		      connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mypasswords","root", "Kulkarni@21");
    		      Statement statement = connection.createStatement();
    		      statement.executeUpdate("INSERT INTO user (name, email, phone, password) VALUES ('" + name + "','" + email + "','" + phone + "','" + pass + "')");
    		    } catch (SQLException ex) {
    		      // Handle any errors
    		      ex.printStackTrace();
    		    } catch (Exception ex) {
    		    	ex.printStackTrace();
    		    }
    		    finally {
    		      try {
    		        if (connection != null) {
    		          connection.close();
    		        }
    		      } catch (SQLException ex) {
    		        // Handle any errors
    		        ex.printStackTrace();
    		      }
    		    }
            	
//            	loginButton.addActionListener(new ActionListener() {
//            		  public void actionPerformed(ActionEvent e) {
//            		  }
//            		});

// Add code for signup buttonn
                new AddPassword();
                frame.dispose();
            }
        });
        buttonPanel.add(signupButton);
        // Add the back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setBackground(new Color(66, 139, 202));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(backButton.getFont().deriveFont(18f));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                frame.dispose();
            }
        });
        buttonPanel.add(backButton);





        // Show the frame
        frame.setVisible(true);
    }

    private JPanel createFormRow(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel.setBackground(new Color(255, 255, 255, 0));

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.DARK_GRAY);
        label.setFont(label.getFont().deriveFont(18f));
        panel.add(label);

        panel.add(field);

        return panel;
    }

}


