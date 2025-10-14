package com.breakout.menu;

import com.breakout.managers.GameManager;
import javax.swing.*;
import java.awt.*;

public class GameMenu {
    private JFrame frame;
    private GameManager gameManager;
    private MenuPanel memuPanel;

    private void initialize() {
        frame = new JFrame("Food Blaster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        memuPanel = new MenuPanel(gameManager);
        frame.add(memuPanel);
    }

    public GameMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        initialize();
    }

    public void showGameOver() {
        JOptionPane.showMessageDialog(frame,
                "Game Over! Bạn đã thua!\nĐộ khó: " + gameManager.getDifficulty(),
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
        showMenu();
    }
}
