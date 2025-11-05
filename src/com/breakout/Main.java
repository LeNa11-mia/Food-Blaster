package com.breakout;

import com.breakout.config.GameConfig;

import javax.swing.*;

/**
 * {@code Main} is the entry point for the Breakout game application.
 * It is responsible for setting up the main window and initializing the game controller.
 */
public class Main {

    /**
     * The main method, which starts the application.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(final String[] args) {
        // Swing GUI operations must run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Setup the main window frame
            final JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setResizable(false);

            // Initialize the singleton Game controller
            Game.initGame(frame);

            frame.setVisible(true);

            // Start the main game loop in a separate thread to keep the EDT responsive
            new Thread(() -> Game.getGame().start()).start();
        });
    }
}