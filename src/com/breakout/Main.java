package com.breakout;

import com.breakout.config.GameConfig;

import javax.swing.*;

/**
 * Main game class - Entry point for the application
 */
public class Main {

    public static void main(String[] args) {
        // Schedules code to run later on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            Game game = new Game(frame);

            // Start with menu
            game.start();
            frame.setVisible(true);
        });
    }
}