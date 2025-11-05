package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the "Game Over" screen displayed when the player loses the game.
 * <p>
 * This panel shows the final score, current level, and provides options
 * to restart the game or return to the main menu. It features a stylized
 * "Game Over" text and a themed background.
 */
public class GameOverPanel extends GUIPanel {
    private JLabel scoreLabel;
    private JLabel difficultyLabel;

    /**
     * Constructs a new {@code GameOverPanel} and initializes its layout,
     * background, labels, and buttons.
     * <p>
     * The panel uses absolute positioning for precise element placement.
     * It displays the player's score and level, and adds "Restart" and
     * "Main Menu" buttons for navigation.
     */
    public GameOverPanel() {
        super(Color.decode("#722F37")); // Cherry wine red

        backgroundImage = GameConfig.GAMEOVER_BACKGROUND;
        setLayout(null);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBounds(150, 220, 300, 300);

        displayInfo(contentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        addButtons(contentPanel);

        add(contentPanel);
    }

    /**
     * Custom painting method that draws the background image and the
     * stylized "Game Over" text in the center of the panel.
     *
     * @param g the {@code Graphics} object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image or fallback color
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.setColor(Color.decode("#722F37"));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw "Game Over" stylized text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font;
        try {
            font = new Font("Brush Script MT", Font.BOLD, 72);
        } catch (Exception e) {
            font = new Font("Segoe Script", Font.BOLD, 72);
        }

        g2d.setFont(font);
        g2d.setColor(new Color(139, 0, 0)); // Dark red

        String text = "Game Over";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 175;

        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    /**
     * Displays the player's score and difficulty (level) in the center panel.
     *
     * @param centerPanel the panel to which the labels are added
     */
    private void displayInfo(JPanel centerPanel) {
        // Score display
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.RED,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Difficulty display
        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                new Color(218, 112, 161),
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);
    }

    /**
     * Updates the displayed score and difficulty level after the game ends.
     *
     * @param finalScore the final score achieved by the player
     * @param level      the level or difficulty reached before the game ended
     */
    public void updateInfo(int finalScore, int level) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            difficultyLabel.setText("Difficulty: " + level);
        }
    }

    /**
     * Adds interactive buttons ("Restart" and "Main Menu") to the game over screen.
     * <p>
     * The "Restart" button restarts the current level, while the
     * "Main Menu" button navigates back to the main menu screen.
     *
     * @param centerPanel the panel to which buttons are added
     */
    private void addButtons(JPanel centerPanel) {
        // Restart button
        JButton restartBtn = createRoundedButton("RESTART", new Color(139, 0, 0));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 55));
        restartBtn.setPreferredSize(new Dimension(300, 55));
        restartBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_PLAYING);
            Game.getGame().getGm().startGame(Game.getGame().getGm().getCurrentLevel());
        });
        centerPanel.add(restartBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Main Menu button
        JButton menuBtn = createRoundedButton("MAIN MENU", new Color(160, 82, 45));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 55));
        menuBtn.setPreferredSize(new Dimension(300, 55));
        menuBtn.addActionListener(e -> {
            Game.getGame().changeState(Defs.STATE_MENU);
        });
        centerPanel.add(menuBtn);
    }

    /**
     * Creates a custom rounded button with smooth color transitions when hovered or pressed.
     *
     * @param text    the text displayed on the button
     * @param bgColor the base background color of the button
     * @return a stylized {@code JButton} with rounded corners and custom effects
     */
    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color with interactive effects
                Color currentColor;
                if (getModel().isPressed()) {
                    int r = Math.max(0, bgColor.getRed() - 30);
                    int g1 = Math.max(0, bgColor.getGreen() - 30);
                    int b = Math.max(0, bgColor.getBlue() - 30);
                    currentColor = new Color(r, g1, b);
                } else if (getModel().isRollover()) {
                    int r = Math.min(255, bgColor.getRed() + 30);
                    int g1 = Math.min(255, bgColor.getGreen() + 30);
                    int b = Math.min(255, bgColor.getBlue() + 30);
                    currentColor = new Color(r, g1, b);
                } else {
                    currentColor = bgColor;
                }

                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Border
                g2d.setColor(new Color(255, 182, 193)); // Light pink
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);

                // Text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 3;
                g2d.drawString(getText(), x, y);

                g2d.dispose();
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
}
