package com.breakout.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for all GUI panels in the game.
 * Provides utility methods for creating styled buttons, labels, and other UI components.
 * Handles shared visual behaviors such as rounded buttons and hover effects.
 */
public abstract class GUIPanel extends JPanel {

    /** Background image for the panel. */
    protected ImageIcon backgroundImage;

    /** Stores the original background colors of buttons for hover effect restoration. */
    public static final Map<JButton, Color> originalColors = new HashMap<>();

    /**
     * Default constructor.
     * Creates an empty panel with no predefined layout or background.
     */
    public GUIPanel() {}

    /**
     * Constructs a panel with the specified background color.
     *
     * @param backgroundColor The background color of the panel.
     */
    public GUIPanel(Color backgroundColor) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
    }

    /**
     * Creates a centered label with specified text, color, and font.
     *
     * @param text  The label text.
     * @param color The text color.
     * @param font  The font used for rendering the text.
     * @return A configured JLabel instance.
     */
    protected JLabel createLabel(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Creates a bordered label with centered text and custom styling.
     *
     * @param text   The label text.
     * @param color  The text color.
     * @param font   The font for the label text.
     * @param border The border applied around the label.
     * @return A bordered JLabel instance.
     */
    protected JLabel createBorderedLabel(String text, Color color, Font font, Border border) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(font);
        label.setBorder(border);
        return label;
    }

    /**
     * Creates a rounded button with custom background, border, and hover animation.
     *
     * @param text         The button text.
     * @param bgColor      The background color.
     * @param borderColor  The border color.
     * @param cornerRadius The roundness of the button corners.
     * @return A JButton with rounded corners and interactive visual effects.
     */
    protected JButton createRoundedButton(String text, Color bgColor, Color borderColor, int cornerRadius) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                try {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Determine button color based on current state (normal, hover, pressed)
                    Color currentColor;
                    if (getModel().isPressed()) {
                        currentColor = new Color(
                                Math.max(0, bgColor.getRed() - 30),
                                Math.max(0, bgColor.getGreen() - 30),
                                Math.max(0, bgColor.getBlue() - 30)
                        );
                    } else if (getModel().isRollover()) {
                        currentColor = new Color(
                                Math.min(255, bgColor.getRed() + 25),
                                Math.min(255, bgColor.getGreen() + 25),
                                Math.min(255, bgColor.getBlue() + 25)
                        );
                    } else {
                        currentColor = bgColor;
                    }

                    // Draw button background
                    g2d.setColor(currentColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

                    // Draw button border
                    g2d.setColor(borderColor);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, cornerRadius, cornerRadius);

                    // Draw text
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(getText());
                    int textHeight = fm.getAscent();
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() + textHeight) / 2 - 3;
                    g2d.drawString(getText(), x, y);
                } finally {
                    g2d.dispose();
                }
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    /**
     * Returns a decorative handwritten-style font if available, otherwise a fallback font.
     *
     * @param style The font style (e.g., Font.PLAIN, Font.BOLD).
     * @param size  The font size.
     * @return A Font object representing a handwritten-style font.
     */
    protected Font getHandwrittenFont(int style, int size) {
        try {
            return new Font("Brush Script MT", style, size);
        } catch (Exception e) {
            return new Font("Segoe Script", style, size);
        }
    }
}
