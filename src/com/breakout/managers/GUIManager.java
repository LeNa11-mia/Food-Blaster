package com.breakout.managers;

import com.breakout.Game;
import com.breakout.gui.*;

import javax.swing.*;

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
    private GameState previousState;  // Lưu state trước khi vào Settings

    public GUIManager(Game game) {
        gameplayPanel = new GameplayPanel(game.getGm());
        menuPanel = new MenuPanel(game);
        gameModesPanel = new GameModesPanel(game);
        winPanel = new WinPanel(game);
        settingPanel = new SettingPanel(game);
        gameOverPanel = new GameOverPanel(game);
        previousState = null;
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
        showGUIPanel(frame, menuPanel);
    }

    public void showSettingsScreen(JFrame frame) {
        showGUIPanel(frame, settingPanel);
    }

    public void showWinScreen(JFrame frame) {
        showGUIPanel(frame, winPanel);
    }

    public void showGameOverScreen(JFrame frame) {
        showGUIPanel(frame, gameOverPanel);
    }

    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    public SettingPanel getSettingPanel() {
        return settingPanel;
    }

    public void setPreviousState(GameState state) {
        this.previousState = state;
    }

    public GameState getPreviousState() {
        return previousState;
    }
}
