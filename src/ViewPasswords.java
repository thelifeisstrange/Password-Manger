import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPasswords {
    public static void main(String[] args) {

   
        // Create the JFrame

        JFrame frame = new JFrame("View Passwords");
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

        // Create the table panel
        JPanel tablePanel = new JPanel();
        mainPanel.add(tablePanel);

        // Add the password table or list
        // Code to populate the table or list with the saved passwords

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(buttonPanel);

        // Add the edit button
        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(100, 50));
        editButton.setBackground(new Color(66, 139, 202));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(editButton.getFont().deriveFont(18f));
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to open the edit password window
            }
        });
        buttonPanel.add(editButton);
        // Add the delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 50));
        deleteButton.setBackground(new Color(66, 139, 202));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(deleteButton.getFont().deriveFont(18f));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to delete the selected password
            }
        });
        buttonPanel.add(deleteButton);

        frame.setVisible(true);
    }
}

