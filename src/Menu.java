import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Menu {
    public static void main(String[] args) {


        // Create the JFrame
        JFrame frame = new JFrame("Passwords");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(450, 450);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Create the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(titlePanel);

        // Add the title label
        JLabel titleLabel = new JLabel("Fetch Your Passwords");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        titleLabel.setForeground(Color.DARK_GRAY);
        titlePanel.add(titleLabel);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(buttonPanel);

        JButton gmailButton = new JButton("Gmail");
        gmailButton.setPreferredSize(new Dimension(100, 50));
        gmailButton.setBackground(new Color(66, 139, 202));
        gmailButton.setForeground(Color.WHITE);
        gmailButton.setFont(gmailButton.getFont().deriveFont(18f));
        gmailButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(gmailButton);

        JButton linkedinButton = new JButton("LinkedIn");
        linkedinButton.setPreferredSize(new Dimension(100, 50));
        linkedinButton.setBackground(new Color(66, 139, 202));
        linkedinButton.setForeground(Color.WHITE);
        linkedinButton.setFont(linkedinButton.getFont().deriveFont(18f));
        linkedinButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(linkedinButton);

        JButton snapchatButton = new JButton("Snapchat");
        snapchatButton.setPreferredSize(new Dimension(100, 50));
        snapchatButton.setBackground(new Color(66, 139, 202));
        snapchatButton.setForeground(Color.WHITE);
        snapchatButton.setFont(snapchatButton.getFont().deriveFont(18f));
        snapchatButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(snapchatButton);

        JButton facebookButton = new JButton("Facebook");
        facebookButton.setPreferredSize(new Dimension(100, 50));
        facebookButton.setBackground(new Color(66, 139, 202));
        facebookButton.setForeground(Color.WHITE);
        facebookButton.setFont(facebookButton.getFont().deriveFont(18f));
        facebookButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(facebookButton);


        JButton instagramButton = new JButton("Instagram");
        instagramButton.setPreferredSize(new Dimension(100, 50));
        instagramButton.setBackground(new Color(66, 139, 202));
        instagramButton.setForeground(Color.WHITE);
        instagramButton.setFont(instagramButton.getFont().deriveFont(18f));
        instagramButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    ;
        buttonPanel.add(instagramButton);
        frame.setVisible(true);
    }
}

