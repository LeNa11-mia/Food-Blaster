package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameManager;
import com.breakout.managers.GameState;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends GUIPanel {

    public GameOverPanel(Game game) {
        super("#722F37"); // Cherry wine red

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.decode("#722F37"));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));

        // Title at the top, score and difficulty in center panel, instructions at the bottom
        displayInfo(game.getGm(), centerPanel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Buttons
        addButtons(game, centerPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void displayInfo(GameManager gm, JPanel centerPanel) {
        // Title
        JLabel titleLabel = createBorderedLabel(
                "GAME OVER",
                Color.decode("#FFB6C1"),
                new Font("Courier", Font.BOLD, 48),
                BorderFactory.createEmptyBorder(80, 0, 30, 0)
        );
        add(titleLabel, BorderLayout.NORTH);

        // Score display
        JLabel scoreLabel = createLabel(
                "Score: " + (gm != null ? gm.getScore() : 0),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 24)
        );
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Difficulty display
        JLabel difficultyLabel = createLabel(
                "Difficulty: " + gm.getDifficulty(),
                Color.WHITE,
                new Font("Arial", Font.PLAIN, 18)
        );
        centerPanel.add(difficultyLabel);

        // Instructions that suggest what player can do after game over
        JLabel instructionLabel = createBorderedLabel(
                "Click buttons to continue",
                Color.decode("#FFB6C1"),
                new Font("Arial", Font.ITALIC, 14),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        );
        add(instructionLabel, BorderLayout.SOUTH);
    }

    private void addButtons(Game game, JPanel centerPanel) {
        // Restart button
        JButton restartBtn = createButton("RESTART", Color.decode("#8B0000")); // Dark red
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setMaximumSize(new Dimension(300, 50));
        restartBtn.addActionListener(e -> {
            game.changeState(GameState.PLAYING);
            game.getGm().startGame(game.getGm().getDifficulty());
        });
        centerPanel.add(restartBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Menu button
        JButton menuBtn = createButton("MAIN MENU", Color.decode("#A0522D")); // Sienna
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.setMaximumSize(new Dimension(300, 50));
        menuBtn.addActionListener(e -> game.changeState(GameState.MENU));
        centerPanel.add(menuBtn);
    }
}
