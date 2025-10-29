package com.breakout.gui;

import com.breakout.Game;
import com.breakout.managers.GameManager;
import com.breakout.managers.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuPanel extends GUIPanel {

    public MenuPanel(Game game) {
        super("#F3CFC6");

        // Title
        JLabel titleLabel = createBorderedLabel(
                "FOOD BLASTER",
                Color.decode("#C9A9A6"),
                new Font("Courier", Font.BOLD, 36),
                BorderFactory.createEmptyBorder(50, 0, 50, 0)
        );
        add(titleLabel, BorderLayout.NORTH);

        add(createButtonPanel(game), BorderLayout.CENTER);

        // Instructions
        JLabel instructionLabel = createBorderedLabel(
                "Use ← → or A D keys to move paddle",
                Color.WHITE,
                new Font("Arial", Font.PLAIN, 14),
                BorderFactory.createEmptyBorder(20, 0, 10, 0)
        );
        add(instructionLabel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(Game game) {
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 100, 150));
        buttonPanel.setBackground(Color.decode("#E0BFB8"));

        // Create buttons with different colors
        JButton easyBtn = createButton("EASY", Color.decode("#F8C8DC"));
        addButton(easyBtn);
        JButton mediumBtn = createButton("MEDIUM", Color.decode("#FFC0CB"));
        addButton(mediumBtn);
        JButton hardBtn = createButton("HARD", Color.decode("#FAA0A0"));
        addButton(hardBtn);
        JButton bossBtn = createButton("BOSS FIGHTS", Color.decode("#F89880"));
        addButton(bossBtn);
        JButton exitBtn = createButton("EXIT", Color.decode("#D8BFD8"));
        addButton(exitBtn);

        // Add action listeners
        easyBtn.addActionListener(e -> {
            game.getGm().startGame("EASY");
            game.changeState(GameState.PLAYING);
        });
        mediumBtn.addActionListener(e -> {
            game.getGm().startGame("MEDIUM");
            game.changeState(GameState.PLAYING);
        });
        hardBtn.addActionListener(e -> {
            game.getGm().startGame("HARD");
            game.changeState(GameState.PLAYING);
        });
        bossBtn.addActionListener(e -> {
            game.getGm().startGame("BOSS");
            game.changeState(GameState.PLAYING);
        });
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(easyBtn);
        buttonPanel.add(mediumBtn);
        buttonPanel.add(hardBtn);
        buttonPanel.add(bossBtn);
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }
}
