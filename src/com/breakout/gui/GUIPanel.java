package com.breakout.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract GUI panel class that provides basic components and features
 * for user interfaces in the Breakout game.
 * Manages backgrounds, buttons with hover effects, and various label types.
 *
 * @author Breakout Team
 * @version 1.0
 */
public abstract class GUIPanel extends JPanel {

    /** Background image of the panel */
    protected ImageIcon backgroundImage;

    /** Map storing original colors of buttons for restoration when needed */
    public static Map<JButton, Color> originalColors = new HashMap<>();

    /**
     * Default constructor creating a panel with default layout.
     */
    public GUIPanel() {}

    /**
     * Constructor creating a panel with specified background color.
     *
     * @param backgroundColor the background color of the panel
     */
    public GUIPanel(Color backgroundColor) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
    }

    /**
     * Adds a button to the original colors management list.
     * This allows for tracking and restoring original button colors.
     *
     * @param button the button to add to the management list
     */
    public void addButton(JButton button) {
        originalColors.put(button, button.getBackground());
    }

    /**
     * Creates a styled button with predefined appearance and hover effects.
     * The button has specified background color, white text, Arial bold font size 18.
     * Features a brightening effect when hovering over the button.
     *
     * @param text the display text on the button
     * @param color the background color of the button
     * @return JButton the created and formatted button
     */
    protected JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect - brightens button when mouse hovers over
        button.addMouseListener(new MouseAdapter() {
            /**
             * Handles mouse enter event - brightens the background color
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            /**
             * Handles mouse exit event - restores the original background color
             */
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    /**
     * Creates a basic styled label.
     * The label is center-aligned horizontally.
     *
     * @param text the display text
     * @param color the text color of the label
     * @param font the font to use
     * @return JLabel the formatted label
     */
    protected JLabel createLabel(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Creates a bordered label with specified styling.
     * The label is center-aligned both horizontally and vertically.
     *
     * @param text the display text
     * @param color the text color of the label
     * @param font the font to use
     * @param border the border of the label
     * @return JLabel the formatted bordered label
     */
    protected JLabel createBorderedLabel(String text, Color color, Font font, Border border) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(font);
        label.setBorder(border);
        return label;
    }
}
