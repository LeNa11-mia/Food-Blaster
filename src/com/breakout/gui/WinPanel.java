package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * WinPanel is displayed when the player successfully completes a level.
 * It shows the player's score, current level, and provides action buttons
 * such as proceeding to the next level, restarting, or returning to the main menu.
 */
public class WinPanel extends GUIPanel {
    private JLabel scoreLabel;
    private JLabel difficultyLabel;

    /**
     * Constructs the WinPanel, initializes background, displays the win message,
     * score information, and control buttons.
     */
    public WinPanel() {
        super(Color.decode("#2D5016")); // Dark green background base
        backgroundImage = GameConfig.WIN_BACKGROUND;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        displayInfo(centerPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        addButtons(centerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Draws the background image stretched to the full panel size.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        try {
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            } else {
                System.out.println("WARNING: backgroundImage is NULL in WinPanel!");
            }
        } finally {
            g2d.dispose();
        }
    }

    /**
     * Displays the title, score, level information, and instruction text.
     *
     * @param centerPanel the panel to which informational components are added
     */
    private void displayInfo(JPanel centerPanel) {
        JLabel titleLabel = createBorderedLabel(
                "YOU WIN!",
                Color.decode("#FF8C69"),
                new Font("Brush Script MT", Font.BOLD, 56),
                BorderFactory.createEmptyBorder(80, 0, 30, 0)
        );
        add(titleLabel, BorderLayout.NORTH);

        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.decode("#FF69B4"),
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                Color.decode("#DDA0DD"),
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);

        JLabel instructionLabel = createBorderedLabel(
                "Click buttons to continue",
                Color.decode("#FFA07A"),
                new Font("Arial", Font.ITALIC, 14),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        );
        add(instructionLabel, BorderLayout.SOUTH);
    }

    /**
     * Updates the score and level text displayed on the panel.
     *
     * @param finalScore the final score the player achieved
     * @param level      the level index the player completed
     */
    public void updateInfo(int finalScore, int level) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            difficultyLabel.setText("Level: " + level);
        }
    }

    /**
     * Adds control buttons such as Next Level, Restart, and Main Menu.
     *
     * @param centerPanel the container to which the buttons are added
     */
    private void addButtons(JPanel centerPanel) {
        GameManager gm = Game.getGame().getGm();

        int nextLevel = gm.getNextDifficulty();
        if (nextLevel > 0) {
            JButton nextBtn = createRoundedButton("NEXT LEVEL", Color.decode("#FF69B4"));
            nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            nextBtn.setMaximumSize(new Dimension(300, 50));
            nextBtn.addActionListener(e -> {
                gm.startGame(nextLevel);
                Game.getGame().changeState(Defs.STATE_PLAYING);
            });
            centerPanel.add(nextBtn);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JButton restartBtn = createRoundedButton("RESTART", Color.decode("#FF8C69"));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 50));
        restartBtn.addActionListener(e -> {
            gm.startGame(gm.getCurrentLevel());
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        centerPanel.add(restartBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton menuBtn = createRoundedButton("MAIN MENU", Color.decode("#DDA0DD"));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_MENU));
        centerPanel.add(menuBtn);
    }

    /**
     * Creates a rounded custom-styled button with color transitions for hover and press states.
     *
     * @param text    the text displayed on the button
     * @param bgColor the background color of the button
     * @return the styled JButton
     */
    private JButton createRoundedButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                try {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    Color currentColor;
                    if (getModel().isPressed()) {
                        currentColor = bgColor.darker();
                    } else if (getModel().isRollover()) {
                        currentColor = bgColor.brighter();
                    } else {
                        currentColor = bgColor;
                    }

                    g2d.setColor(currentColor);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                    g2d.setColor(new Color(255, 255, 255, 150));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 25, 25);

                    g2d.setColor(Color.WHITE);
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.drawString(text, (getWidth() - fm.stringWidth(text)) / 2,
                            (getHeight() + fm.getAscent()) / 2 - 2);
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
        button.setPreferredSize(new Dimension(300, 50));

        return button;
    }
}
