package com.passwordmanager.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;

/**
 * Utility class for common UI operations and styling
 */
public class UIUtil {
    
    // Modern color scheme - Material palette
    public static final Color PRIMARY_COLOR = new Color(25, 118, 210);    // Material Blue 700
    public static final Color SECONDARY_COLOR = new Color(21, 101, 192);  // Material Blue 800
    public static final Color ACCENT_COLOR = new Color(255, 145, 0);      // Material Orange 600
    public static final Color SUCCESS_COLOR = new Color(67, 160, 71);     // Material Green 600
    public static final Color ERROR_COLOR = new Color(211, 47, 47);       // Material Red 600
    public static final Color BACKGROUND_COLOR = new Color(245, 245, 245);// Grey 100
    public static final Color CARD_COLOR = new Color(255, 255, 255);      // White
    public static final Color TEXT_COLOR = new Color(33, 33, 33);         // Grey 900
    public static final Color SECONDARY_TEXT_COLOR = new Color(97, 97, 97);// Grey 700
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    public static final Color HOVER_COLOR = new Color(41, 182, 246);      // Material Light Blue 400
    
    // Modern fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    // Common dimensions
    public static final Dimension BUTTON_SIZE = new Dimension(180, 48);
    public static final Dimension FIELD_SIZE = new Dimension(300, 40);
    public static final int DEFAULT_PADDING = 25;
    public static final int CORNER_RADIUS = 10;
    
    /**
     * Creates a panel with a modern gradient background
     * 
     * @return A JPanel with modern gradient background
     */
    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(
                    0, 0, 
                    new Color(21, 101, 192), // Darker blue top left
                    getWidth(), getHeight(), 
                    new Color(25, 118, 210)  // Lighter blue bottom right
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Optional: Add a subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 15));
                for (int i = 0; i < getHeight(); i += 4) {
                    g2d.drawLine(0, i, getWidth(), i);
                }
            }
        };
    }

    /**
     * Creates a panel with a card-like appearance
     * 
     * @return A JPanel styled as a card
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(CARD_COLOR);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), CORNER_RADIUS * 2, CORNER_RADIUS * 2));
                
                // Add subtle shadow effect
                g2d.setColor(new Color(0, 0, 0, 10));
                for (int i = 0; i < 5; i++) {
                    g2d.draw(new RoundRectangle2D.Double(i, i, getWidth() - (i * 2), getHeight() - (i * 2), CORNER_RADIUS * 2, CORNER_RADIUS * 2));
                }
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));
        return panel;
    }
    
    /**
     * Creates a styled title label
     * 
     * @param text The text for the label
     * @return A styled JLabel
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    /**
     * Creates a styled button
     * 
     * @param text The text for the button
     * @param listener The action listener for the button
     * @return A styled JButton
     */
    public static JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(SECONDARY_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(HOVER_COLOR);
                } else {
                    g2d.setColor(PRIMARY_COLOR);
                }
                
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS));
                
                // Add a subtle highlight at the top
                GradientPaint highlight = new GradientPaint(
                    0, 0,
                    new Color(255, 255, 255, 100),
                    0, getHeight() / 2,
                    new Color(255, 255, 255, 0)
                );
                g2d.setPaint(highlight);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight() / 2, CORNER_RADIUS, CORNER_RADIUS));
                
                super.paintComponent(g);
            }
        };
        
        button.setPreferredSize(BUTTON_SIZE);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(BUTTON_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(BUTTON_TEXT_COLOR);
            }
        });
        
        if (listener != null) {
            button.addActionListener(listener);
        }
        
        return button;
    }
    
    /**
     * Creates an accent colored button
     * 
     * @param text The text for the button
     * @param listener The action listener for the button
     * @return A styled JButton
     */
    public static JButton createAccentButton(String text, ActionListener listener) {
        JButton button = createButton(text, listener);
        
        // Override painting to use accent color
        button.addPropertyChangeListener("UI", evt -> {
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
        });
        
        return button;
    }
    
    /**
     * Creates a styled text field with modern look
     *
     * @param placeholder Placeholder text
     * @return A styled JTextField
     */
    public static JTextField createTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Add placeholder text if empty
                if (getText().isEmpty() && !placeholder.isEmpty() && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setColor(new Color(150, 150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    
                    g2d.drawString(placeholder, x, y);
                }
            }
        };
        
        field.setPreferredSize(FIELD_SIZE);
        field.setFont(FIELD_FONT);
        field.setMargin(new Insets(8, 10, 8, 10));
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Add focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
                field.setBackground(Color.WHITE);
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                field.setBackground(new Color(240, 240, 240));
            }
        });
        
        return field;
    }
    
    /**
     * Creates a styled password field with modern look
     *
     * @param placeholder Placeholder text
     * @return A styled JPasswordField
     */
    public static JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Add placeholder text if empty
                if (getPassword().length == 0 && !placeholder.isEmpty() && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setColor(new Color(150, 150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    
                    g2d.drawString(placeholder, x, y);
                }
            }
        };
        
        field.setPreferredSize(FIELD_SIZE);
        field.setFont(FIELD_FONT);
        field.setMargin(new Insets(8, 10, 8, 10));
        field.setBackground(new Color(240, 240, 240));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Add focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
                field.setBackground(Color.WHITE);
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                field.setBackground(new Color(240, 240, 240));
            }
        });
        
        return field;
    }
    
    /**
     * Creates a styled form row with label and field
     * 
     * @param labelText The text for the label
     * @param field The input field component
     * @return A panel containing the label and field
     */
    public static JPanel createFormRow(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(15, 10));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Sets up the standard layout for a frame
     * 
     * @param frame The frame to set up
     * @param title The title for the frame
     * @param width The width of the frame
     * @param height The height of the frame
     */
    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center on screen
        frame.setResizable(true);
        
        // Create main panel with gradient background
        JPanel mainPanel = createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
        
        frame.setContentPane(mainPanel);
    }
    
    /**
     * Shows a styled error message dialog
     * 
     * @param parent The parent component
     * @param message The error message
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Shows a styled information message dialog
     * 
     * @param parent The parent component
     * @param message The information message
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent, 
            message, 
            "Information", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Shows a confirmation dialog and returns the user's choice
     * 
     * @param parent The parent component
     * @param message The confirmation message
     * @return true if the user confirmed, false otherwise
     */
    public static boolean showConfirmation(Component parent, String message) {
        int choice = JOptionPane.showConfirmDialog(
            parent, message, "Confirm", JOptionPane.YES_NO_OPTION
        );
        return choice == JOptionPane.YES_OPTION;
    }
    
    /**
     * Creates a responsive panel that adjusts its layout based on available space
     * 
     * @param content The content to display in the panel
     * @return A responsive panel
     */
    public static JPanel createResponsivePanel(Component content) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        wrapper.add(content, gbc);
        
        return wrapper;
    }
    
    /**
     * Makes a component resize properly with the window
     * 
     * @param component The component to make responsive
     */
    public static void makeResponsive(JComponent component) {
        component.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && component.isShowing()) {
                updateComponentSize(component);
            }
        });
        
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateComponentSize(component);
            }
        });
    }
    
    /**
     * Updates component size based on parent container
     * 
     * @param component The component to update
     */
    private static void updateComponentSize(JComponent component) {
        Container parent = component.getParent();
        if (parent != null) {
            int parentWidth = parent.getWidth();
            int parentHeight = parent.getHeight();
            
            // Calculate responsive dimensions (90% of parent, with minimum size)
            int width = Math.max(300, (int)(parentWidth * 0.9));
            int height = Math.max(250, (int)(parentHeight * 0.9));
            
            // Update preferred size if needed
            Dimension currentSize = component.getPreferredSize();
            if (currentSize.width != width || currentSize.height != height) {
                component.setPreferredSize(new Dimension(width, height));
                component.revalidate();
            }
        }
    }
} 