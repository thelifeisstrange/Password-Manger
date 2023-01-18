import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

public class Login {
    Login() {
        // Create the frame
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 450);

        // Create the main panel
        JPanel mainPanel = new JPanel(){
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
        JLabel titleLabel = new JLabel("Login to your account");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        titleLabel.setForeground(Color.DARK_GRAY);
        titlePanel.add(titleLabel);

        // Create the form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel);

        // Add the username label and field
        formPanel.add(createFormRow("Username:", new JTextField(20)));

        // Add the password label and field
        formPanel.add(createFormRow("Password:", new JPasswordField(20)));

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel);

        //Add Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setBackground(new Color(66, 139, 202));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(backButton.getFont().deriveFont(18f));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WelcomePage();
                frame.dispose();
            }
        });
        buttonPanel.add(backButton);

        // Add the login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 50));
        loginButton.setBackground(new Color(66, 139, 202));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(loginButton.getFont().deriveFont(18f));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(loginButton);

        // Add the action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                 new AddPassword();
                 frame.dispose();
                }
            }
        );
        // Show the frame
        frame.setVisible(true);
    }

    private static JPanel createFormRow(String labelText, JTextField field) {
        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        // Add the label
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        // Add the field
        field.setPreferredSize(new Dimension(200, 20));
        panel.add(field);

        return panel;
    }

    // Dummy method to check the username and password
    private static boolean checkCredentials(String username, char[] password) {
        return username.equals("admin") && new String(password).equals("password");
    }
}


//
