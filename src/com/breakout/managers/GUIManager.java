package com.breakout.managers;

import com.breakout.Game;
import com.breakout.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI manager
 */
public class GUIManager {
    private GameplayPanel gameplayPanel;
    private MenuPanel menuPanel;
    private WinPanel winPanel;
    private GameOverPanel gameOverPanel;

    public GUIManager(Game game) {
        gameplayPanel = new GameplayPanel(game.getGm());
        menuPanel = new MenuPanel(game);
        winPanel = new WinPanel(game);
        gameOverPanel = new GameOverPanel(game);
    }

    public void resetButton(Map<JButton, Color> originalColors) {
        for (JButton btn : originalColors.keySet()) {
            btn.setBackground(originalColors.get(btn));
        }
    }

    private void showGUIPanel(JFrame frame, GUIPanel panel) {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.add(panel);
            frame.revalidate();
            frame.repaint();

            panel.setFocusable(true);
            panel.requestFocusInWindow();
        });
    }

    public void showGameplayPanel(JFrame frame) {
        showGUIPanel(frame, gameplayPanel);
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    public void showMenuScreen(JFrame frame) {
        showGUIPanel(frame, menuPanel);
    }

    public void showWinScreen(JFrame frame) {
        showGUIPanel(frame, winPanel);
    }

    public void showGameOverScreen(JFrame frame) {
        showGUIPanel(frame, gameOverPanel);
    }
}
