import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPassword {
    AddPassword() {
        // Create the JFrame
        JFrame frame = new JFrame("Add Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 450);

        // Create the main pane
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

        // Create the form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel);

        // Add the website name field
        JLabel websiteLabel = new JLabel("Website name: ");
        JTextField websiteField = new JTextField();
        formPanel.add(websiteLabel);
        formPanel.add(websiteField);

        // Add the username field
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // Add the password field
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel);

        // Add the save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setBackground(new Color(66, 139, 202));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(saveButton.getFont().deriveFont(18f));
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
// Code to save the password to the user's password manager
                frame.dispose();
            }
        });
        buttonPanel.add(saveButton);
        frame.setVisible(true);
    }


}