package com.breakout.gui;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.GameManager;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends GUIPanel {
    private JLabel scoreLabel;
    private JLabel difficultyLabel;

    private static final Color NEXT_LEVEL_BG = Color.decode("#228B22"); // Forest green
    private static final Color RESTART_BG = Color.decode("#6B8E23"); // Olive drab
    private static final Color MENU_BG = Color.decode("#8B7355"); // Burlywood
    private static final Color BORDER_COLOR = Color.WHITE; // Viền trắng
    private static final int CORNER_RADIUS = 15; // Bo góc nhỏ hơn một chút

    public WinPanel() {
        super(Color.decode("#2D5016")); // Dark green
        backgroundImage = GameConfig.WIN_BACKGROUND;

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.decode("#2D5016"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        // Title at the top, score and difficulty in center panel, instructions at the bottom
        displayInfo(centerPanel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        addButtons(centerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void displayInfo(JPanel centerPanel) {
        // Title
        JLabel titleLabel = createBorderedLabel(
                "YOU WIN!",
                Color.decode("#90EE90"),
                new Font("Courier", Font.BOLD, 48),
                BorderFactory.createEmptyBorder(80, 0, 30, 0)
        );
        add(titleLabel, BorderLayout.NORTH);

        // Score display
        scoreLabel = createLabel(
                "Score: " + (Game.getGame().getGm() != null ? Game.getGame().getGm().getScore() : 0),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Difficulty display
        difficultyLabel = createLabel(
                "Level: " + Game.getGame().getGm().getCurrentLevel(),
                Color.WHITE,
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);

        // Instructions at bottom
        JLabel instructionLabel = createBorderedLabel(
                "Click buttons to continue",
                Color.decode("#90EE90"),
                new Font("Arial", Font.ITALIC, 14),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        );
        add(instructionLabel, BorderLayout.SOUTH);
    }

    public void updateInfo(int finalScore, int level) {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + finalScore);
        }
        if (difficultyLabel != null) {
            difficultyLabel.setText("Level: " + level);
        }
    }

    private void addButtons(JPanel centerPanel) {
        GameManager gm = Game.getGame().getGm();
        // Next level button
        int nextLevel = Game.getGame().getGm().getNextDifficulty();
        if (nextLevel > 0) {
            // SỬ DỤNG createRoundedButton
            JButton nextBtn = createRoundedButton("NEXT LEVEL", NEXT_LEVEL_BG, BORDER_COLOR, CORNER_RADIUS);
            nextBtn.setFont(new Font("Arial", Font.BOLD, 18));
            nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            nextBtn.setMaximumSize(new Dimension(300, 50));
            nextBtn.addActionListener(e -> {
                gm.startGame(gm.getNextDifficulty());
                Game.getGame().changeState(Defs.STATE_PLAYING);

            });
            centerPanel.add(nextBtn);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // Restart button
        JButton restartBtn = createRoundedButton("RESTART", RESTART_BG, BORDER_COLOR, CORNER_RADIUS);
        restartBtn.setFont(new Font("Arial", Font.BOLD, 18));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 50));
        restartBtn.addActionListener(e -> {
            gm.startGame(gm.getCurrentLevel());
            Game.getGame().changeState(Defs.STATE_PLAYING);
        });
        centerPanel.add(restartBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Menu button
        JButton menuBtn = createRoundedButton("MAIN MENU", MENU_BG, BORDER_COLOR, CORNER_RADIUS);
        menuBtn.setFont(new Font("Arial", Font.BOLD, 18));
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.addActionListener(e -> Game.getGame().changeState(Defs.STATE_MENU));
        centerPanel.add(menuBtn);
    }
}