package com.breakout;

import com.breakout.config.GameConfig;

import javax.swing.*;

/**
 * Main game class - Entry point for the application
 */
public class Main {

    public static void main(String[] args) {
        // Các thao tác xử lý giao diện của Swing chạy trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(GameConfig.WINDOW_TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            Game.initGame(frame); // Khởi tạo game, chưa vào game loop

            frame.setVisible(true);

            new Thread(() -> Game.getGame().start()).start(); // Tạo luồng riêng cho game loop
        });
    }
}
