package com.breakout;

import javax.swing.*;

/**
 * Main game class - Entry point for the application
 */
public class Main {
    // Screen dimensions
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;

    public static void main(String[] args) {
        // Schedules code to run later on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Food Blaster");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            Game game = new Game(frame);

            // Start with menu
            game.start();
            frame.setVisible(true);
        });
    }
}
