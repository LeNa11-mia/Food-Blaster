package com.breakout.managers;

import com.breakout.Game;
import com.breakout.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * GUI manager
 */
public class GUIManager {
    private GameplayPanel gameplayPanel;
    private MenuPanel menuPanel;
    private SettingPanel settingPanel;
    private GameModesPanel gameModesPanel;
    private WinPanel winPanel;
    private GameOverPanel gameOverPanel;
    private int previousState;  // Lưu state trước khi vào Settings

    public GUIManager() {
        gameplayPanel = new GameplayPanel();
        menuPanel = new MenuPanel();
        gameModesPanel = new GameModesPanel();
        winPanel = new WinPanel();
        settingPanel = new SettingPanel();
        gameOverPanel = new GameOverPanel();
        previousState = -1;
    }

    public void resetButton(Map<JButton, Color> originalColors) {
        for (JButton btn : originalColors.keySet()) {
            btn.setBackground(originalColors.get(btn));
        }
    }

    private void showGUIPanel(JFrame frame, GUIPanel panel) {
        // Xử lý giao diện trên luồng riêng (EDT)
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

    public void showGameModesScreen(JFrame frame) {
        showGUIPanel(frame, gameModesPanel);
    }

    public void showMenuScreen(JFrame frame) {
        showGUIPanel(frame, menuPanel);
    }

    public void showSettingsScreen(JFrame frame) {
        showGUIPanel(frame, settingPanel);
    }

    public void showWinScreen(JFrame frame) {
        GameManager gm = Game.getGame().getGm();
        winPanel.updateInfo(gm.getScore(), gm.getDifficultyName());
        showGUIPanel(frame, winPanel);
    }

    public void showGameOverScreen(JFrame frame) {
        GameManager gm = Game.getGame().getGm();
        gameOverPanel.updateInfo(gm.getScore(), gm.getDifficultyName());
        showGUIPanel(frame, gameOverPanel);
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    public SettingPanel getSettingPanel() {
        return settingPanel;
    }

    public void setPreviousState(int state) {
        this.previousState = state;
    }

    public int getPreviousState() {
        return previousState;
    }
}
