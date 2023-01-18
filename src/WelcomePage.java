import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WelcomePage {
    WelcomePage() {
        // Create the JFrame
        JFrame frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
//        frame.setLocation(-50,-50);
        frame.setSize(450, 450);

        // Create the main pane
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
        JLabel titleLabel = new JLabel("Welcome to our application!");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel);


        JButton signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(100, 50));
        signupButton.setBackground(new Color(66, 139, 202));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(signupButton.getFont().deriveFont(18f));
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Signup();
                frame.dispose();
            }
        });
        buttonPanel.add(signupButton);



        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 50));
        loginButton.setBackground(new Color(66, 139, 202));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(loginButton.getFont().deriveFont(18f));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
            }
        });
        buttonPanel.add(loginButton);

        frame.setVisible(true);
    }
}
