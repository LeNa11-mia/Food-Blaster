package com.breakout.menu;

import com.breakout.managers.GameManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
    private GameManager gameManager;
    private JLabel titleLabel;

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 220));

        titleLabel = new JLabel("FOOD BLASTER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        buttonPanel.setBackground(new Color(255, 250, 240));

        MenuButton easyBtn = new MenuButton("EASY", Color.GREEN, new Color(0, 200, 0));
        MenuButton mediumBtn = new MenuButton("MEDIUM", Color.YELLOW, new Color(255, 255, 100));
        MenuButton hardBtn = new MenuButton("HARD", Color.ORANGE, new Color(255, 200, 100));
        MenuButton exitBtn = new MenuButton("EXIT", Color.GRAY, Color.LIGHT_GRAY);

        easyBtn.addActionListener(e -> startGame("EASY"));
        mediumBtn.addActionListener(e -> startGame("MEDIUM"));
        hardBtn.addActionListener(e -> startGame("HARD"));
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(easyBtn);
        buttonPanel.add(mediumBtn);
        buttonPanel.add(hardBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void startGame(String difficulty){
        gameManager.setDifficulty(difficulty);
        gameManager.startGame();
    }

    public MenuPanel(GameManager gameManager){
        this.gameManager=gameManager;
        setupUI();
    }

}
