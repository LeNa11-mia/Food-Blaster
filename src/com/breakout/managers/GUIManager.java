package com.breakout.managers;

import com.breakout.Game;
import com.breakout.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Manages all GUI panels and handles switching between different screens
 * in the game's user interface.
 *
 * <p>This class centralizes GUI navigation logic, making transitions between
 * menu, gameplay, settings, and game result screens consistent and easy to maintain.</p>
 */
public class GUIManager {

    private GameplayPanel gameplayPanel;
    private MenuPanel menuPanel;
    private SettingPanel settingPanel;
    private GameModesPanel gameModesPanel;
    private WinPanel winPanel;
    private GameOverPanel gameOverPanel;

    /** Stores the previous UI state before opening settings (if needed). */
    private int previousState;

    /**
     * Creates and initializes all GUI panels.
     */
    public GUIManager() {
        gameplayPanel = new GameplayPanel();
        menuPanel = new MenuPanel();
        gameModesPanel = new GameModesPanel();
        winPanel = new WinPanel();
        gameOverPanel = new GameOverPanel();
        settingPanel = new SettingPanel();
        previousState = -1;
    }

    /**
     * Restores the original background color of buttons, typically after hover effects.
     *
     * @param originalColors A map storing each button and its original background color.
     */
    public void resetButton(Map<JButton, Color> originalColors) {
        for (JButton btn : originalColors.keySet()) {
            btn.setBackground(originalColors.get(btn));
        }
    }

    /**
     * Replaces the current panel displayed in the given frame with another GUI panel.
     *
     * @param frame The main game window.
     * @param panel The panel to display.
     */
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

    /**
     * Displays the gameplay screen.
     */
    public void showGameplayPanel(JFrame frame) {
        showGUIPanel(frame, gameplayPanel);
    }

    /**
     * Displays the game mode selection screen.
     */
    public void showGameModesScreen(JFrame frame) {
        showGUIPanel(frame, gameModesPanel);
    }

    /**
     * Displays the main menu screen.
     * Updates menu state before showing it.
     */
    public void showMenuScreen(JFrame frame) {
        menuPanel.updateMenu();
        showGUIPanel(frame, menuPanel);
    }

    /**
     * Displays the settings screen.
     */
    public void showSettingsScreen(JFrame frame) {
        showGUIPanel(frame, settingPanel);
    }

    /**
     * Displays the win screen and shows level score and progress.
     */
    public void showWinScreen(JFrame frame) {
        GameManager gm = Game.getGame().getGm();
        winPanel.updateInfo(gm.getScore(), gm.getCurrentLevel());
        showGUIPanel(frame, winPanel);
    }

    /**
     * Displays the game-over screen and shows score information.
     */
    public void showGameOverScreen(JFrame frame) {
        GameManager gm = Game.getGame().getGm();
        gameOverPanel.updateInfo(gm.getScore(), gm.getCurrentLevel());
        showGUIPanel(frame, gameOverPanel);
    }

    // ----------- Getters -----------

    public GameplayPanel getGameplayPanel() { return gameplayPanel; }
    public SettingPanel getSettingPanel() { return settingPanel; }
    public MenuPanel getMenuPanel() { return menuPanel; }
    public GameModesPanel getGameModesPanel() { return gameModesPanel; }
    public WinPanel getWinPanel() { return winPanel; }
    public GameOverPanel getGameOverPanel() { return gameOverPanel; }

    // ----------- Previous State Handling -----------

    /**
     * Stores the previous UI state (used when returning from settings or pause menu).
     *
     * @param state The previous state constant.
     */
    public void setPreviousState(int state) {
        this.previousState = state;
    }

    /**
     * @return The previously stored UI state.
     */
    public int getPreviousState() {
        return previousState;
    }

    /**
     * Refreshes the menu UI state, typically enabling or disabling buttons
     * depending on whether the game can continue.
     */
    public void refreshMenu() {
        if (menuPanel != null) {
            menuPanel.refreshMenu();
        }
    }
}
