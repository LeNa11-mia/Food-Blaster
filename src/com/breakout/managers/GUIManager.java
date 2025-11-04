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
        gameOverPanel = new GameOverPanel();
        settingPanel = new SettingPanel();
        previousState = -1;
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

    public void showGameModesScreen(JFrame frame) {
        showGUIPanel(frame, gameModesPanel);
    }

    public void showMenuScreen(JFrame frame) {
        menuPanel.updateMenu();
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

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public GameModesPanel getGameModesPanel() {
        return gameModesPanel;
    }

    public WinPanel getWinPanel() {
        return winPanel;
    }

    public GameOverPanel getGameOverPanel() {
        return gameOverPanel;
    }

    public void setPreviousState(int state) {
        this.previousState = state;
    }

    public int getPreviousState() {
        return previousState;
    }

    /**
     * Cập nhật trạng thái menu (ví dụ: enable/disable nút continue)
     */
    public void refreshMenu() {
        if (menuPanel != null) {
            menuPanel.refreshMenu();
        }
    }

    /**
     * Cập nhật thông tin win screen
     */
    public void updateWinScreen(int score, String difficulty) {
        if (winPanel != null) {
            winPanel.updateInfo(score, difficulty);
        }
    }

    /**
     * Cập nhật thông tin game over screen
     */
    public void updateGameOverScreen(int score, String difficulty) {
        if (gameOverPanel != null) {
            gameOverPanel.updateInfo(score, difficulty);
        }
    }
}
