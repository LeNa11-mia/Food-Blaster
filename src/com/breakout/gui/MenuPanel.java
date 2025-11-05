package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main menu screen of the game.
 * <p>
 * This panel displays the game title and a set of interactive buttons such as
 * "Play", "Continue" (if a saved game exists), and "Exit".
 * It also draws the themed background and stylized title text.
 */
public class MenuPanel extends GUIPanel {

    private JButton continueButton;

    /**
     * Constructs the main menu panel and initializes the layout,
     * background image, and button interface.
     * <p>
     * The menu uses absolute positioning to ensure predictable placement
     * across different screen resolutions.
     */
    public MenuPanel() {
        super(Color.decode("#F3CFC6"));

        // Load background image
        backgroundImage = GameConfig.MENU_BACKGROUND;

        setLayout(null); // Use absolute positioning

        // Create and position the button panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150); // x, y, width, height
        add(buttonPanel);
    }

    /**
     * Paints the background and draws the stylized game title text
     * at the top section of the menu.
     *
     * @param g the {@code Graphics} object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw full-screen background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the game title text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font;
        try {
            font = new Font("Brush Script MT", Font.BOLD, 72);
        } catch (Exception e) {
            font = new Font("Segoe Script", Font.BOLD, 72);
        }

        g2d.setFont(font);
        g2d.setColor(new Color(255, 105, 180)); // Pastel pink

        String text = "Food Blaster";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 120;

        g2d.drawString(text, x, y);
    }

    /**
     * Creates the panel containing the menu buttons.
     * <p>
     * If a saved game exists, a "Continue" button is also added.
     *
     * @return a configured {@code JPanel} containing menu buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        boolean hasSave = SaveManager.saveExists();

        // Grid layout changes depending on save availability
        if (hasSave) {
            buttonPanel.setLayout(new GridLayout(3, 1, 15, 20));
        } else {
            buttonPanel.setLayout(new GridLayout(2, 1, 15, 20));
        }

        buttonPanel.setOpaque(false);

        // Play button → leads to game mode selection
        JButton playBtn = createRoundedButton("PLAY", Color.decode("#F8C8DC"));
        playBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_GAME_MODES));
        buttonPanel.add(playBtn);

        // Continue button → available only when a save file exists
        if (hasSave) {
            continueButton = createRoundedButton("CONTINUE", Color.decode("#FFB6C1"));
            continueButton.addActionListener(e -> Game.getGame().startContinueGame());
            buttonPanel.add(continueButton);
        }

        // Exit button → quits the application
        JButton exitBtn = createRoundedButton("EXIT", Color.decode("#D8BFD8"));
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }

    /**
     * Rebuilds the menu layout, usually after game save data changes.
     * <p>
     * This ensures that the "Continue" button appears or disappears
     * depending on save availability.
     */
    public void updateMenu() {
        removeAll();

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(150, 400, 300, 150);
        add(buttonPanel);
    }

    /**
     * Creates a rounded-corner styled button with smooth hover and click effects.
     *
     * @param text    the text displayed on the button
     * @param bgColor the base background color of the button
     * @return a custom UI-styled {@code JButton}
     */
    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                try {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Determine color based on interaction state
                    Color currentColor;
                    if (getModel().isPressed()) {
                        currentColor = bgColor.darker();
                    } else if (getModel().isRollover()) {
                        currentColor = bgColor.brighter();
                    } else {
                        currentColor = bgColor;
                    }

                    g2d.setColor(currentColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                    // Border
                    g2d.setColor(Color.PINK);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                    // Text
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(getText());
                    int textHeight = fm.getAscent();
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() + textHeight) / 2 - 2;
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
        button.setPreferredSize(new Dimension(200, 50));

        return button;
    }

    /**
     * Enables or disables the "Continue" button depending on whether
     * the game state allows continuation.
     *
     * @param enabled true to enable the button, false to disable
     */
    public void setContinueButtonEnabled(boolean enabled) {
        if (continueButton != null) {
            continueButton.setEnabled(Game.getGame().getGm().canContinueGame());
        }
    }

    /**
     * Refreshes the menu screen by rebuilding and repainting components.
     * Useful when external conditions change (e.g., save file added or removed).
     */
    public void refreshMenu() {
        updateMenu();
    }
}
