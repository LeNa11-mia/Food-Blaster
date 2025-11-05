package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.Level;

import javax.swing.*;
import java.awt.*;

/**
 * Game Modes selection panel that allows players to choose different levels.
 * Displays a grid of level buttons with unlock status and visual feedback.
 * Features rounded buttons with hover effects and level locking mechanism.
 *
 * @author Breakout Team
 * @version 1.0
 */
public class GameModesPanel extends GUIPanel {

    /** Array of level selection buttons */
    private JButton[] buttons;

    /**
     * Constructor initializes the game modes panel with background and layout.
     * Sets up the level selection interface with visual styling.
     */
    public GameModesPanel() {
        super(Color.decode("#F3CFC6"));
        backgroundImage = GameConfig.GAMEMODES_BACKGROUND;

        setLayout(null); // Use absolute positioning for precise placement

        // Initialize level selection buttons array
        buttons = new JButton[GameConfig.TOTAL_LEVELS];

        // Create and position the modes selection panel
        JPanel modesPanel = createModesPanel();
        modesPanel.setBounds(125, 280, 350, 320); // x, y, width, height
        add(modesPanel);
    }

    /**
     * Custom painting method that draws the background image and title text.
     * Renders the "Select Level" title with hand-written style font.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image covering the entire screen
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#FFC0CB"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw "Select Level" text in pink hand-written style
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font;
        try {
            font = new Font("Brush Script MT", Font.BOLD, 60);
        } catch (Exception e) {
            font = new Font("Segoe Script", Font.BOLD, 60);
        }

        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180)); // Pastel pink color

        String text = "Select Level";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 150;

        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    /**
     * Creates the main panel containing level selection grid and back button.
     * Organizes level buttons in a grid layout with proper spacing.
     *
     * @return JPanel the configured modes selection panel
     */
    private JPanel createModesPanel() {
        JPanel modesPanel = new JPanel();
        modesPanel.setLayout(new BorderLayout()); // Level buttons in center, BACK button at bottom
        modesPanel.setOpaque(false);

        JPanel levelGrid = new JPanel();
        levelGrid.setLayout(new GridLayout(2, 3, 25, 25));
        // Level selection buttons arranged in grid, spaced 25 pixels apart
        levelGrid.setOpaque(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

        // Create compact buttons with different colors
        JButton backBtn = createRoundedButton("← BACK", Color.decode("#D8BFD8"), Defs.GO_BACK);
        bottomPanel.add(backBtn);

        // Create level selection buttons
        for (int i = 0; i < GameConfig.TOTAL_LEVELS; i++) {
            buttons[i] = createRoundedButton(Integer.toString(i + 1), Color.decode("#FFC0CB"), i + 1);
            levelGrid.add(buttons[i]);
        }

        modesPanel.add(levelGrid, BorderLayout.CENTER);
        modesPanel.add(bottomPanel, BorderLayout.SOUTH);

        return modesPanel;
    }

    /**
     * Creates a custom rounded button with visual effects and level locking logic.
     * Buttons change color on hover/press and show locked state for unavailable levels.
     *
     * @param text the button display text
     * @param bgColor the base background color of the button
     * @param mode the game mode or level number (Defs.GO_BACK for back button)
     * @return JButton the created rounded button with custom behavior
     */
    private JButton createRoundedButton(String text, Color bgColor, int mode) {
        JButton button = new JButton(text) {
            /**
             * Custom painting for rounded buttons with visual states.
             * Handles normal, hover, pressed, and locked states with different colors.
             *
             * @param g the Graphics object for painting
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color with visual effects
                Color currentColor;
                if (getModel().isPressed()) {
                    int r = Math.max(0, bgColor.getRed() - 30);
                    int g1 = Math.max(0, bgColor.getGreen() - 30);
                    int b = Math.max(0, bgColor.getBlue() - 30);
                    currentColor = new Color(r, g1, b);
                } else if (getModel().isRollover()) {
                    int r = Math.min(255, bgColor.getRed() + 20);
                    int g1 = Math.min(255, bgColor.getGreen() + 20);
                    int b = Math.min(255, bgColor.getBlue() + 20);
                    currentColor = new Color(r, g1, b);
                } else {
                    currentColor = bgColor;
                }

                // Handle locked levels - display in gray
                if (mode != Defs.GO_BACK && !Level.isLevelUnlocked(mode)) {
                    g2d.setColor(Color.LIGHT_GRAY); // Locked levels appear gray
                } else {
                    g2d.setColor(currentColor);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Pink border
                g2d.setColor(Color.PINK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(300, 50)); // More compact button size

        // Add action listeners for button functionality
        button.addActionListener(e -> {
            if (mode == Defs.GO_BACK) {
                Game.getGame().changeState(Defs.STATE_MENU);
            } else if (Level.isLevelUnlocked(mode)) { // Check if level is unlocked
                Game.getGame().getGm().startGame(mode);
                Game.getGame().changeState(Defs.STATE_PLAYING);
            }
        });

        return button;
    }
}
