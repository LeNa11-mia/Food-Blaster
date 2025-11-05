package com.breakout.managers;

import com.breakout.Game;
import com.breakout.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * {@code GUIManager} is responsible for initializing and managing all user interface
 * panels (screens) and switching between them within the main game frame.
 */
public class GUIManager {
    private final GameplayPanel gameplayPanel;
    private final MenuPanel menuPanel;
    private final SettingPanel settingPanel;
    private final GameModesPanel gameModesPanel;
    private final WinPanel winPanel;
    private final GameOverPanel gameOverPanel;
    private int previousState;  // Stores the state before entering Settings

    /**
     * Initializes all graphical user interface panels.
     */
    public GUIManager() {
        this.gameplayPanel = new GameplayPanel();
        this.menuPanel = new MenuPanel();
        this.gameModesPanel = new GameModesPanel();
        this.winPanel = new WinPanel();
        this.gameOverPanel = new GameOverPanel();
        this.settingPanel = new SettingPanel();
        this.previousState = -1;
    }

    /**
     * Resets the background color of a set of JButtons to their original colors.
     *
     * @param originalColors A map containing the buttons and their original colors.
     */
    public void resetButton(final Map<JButton, Color> originalColors) {
        for (final JButton btn : originalColors.keySet()) {
            btn.setBackground(originalColors.get(btn));
        }
    }

    /**
     * Helper method to switch the main frame's content pane to a new GUI panel.
     *
     * @param frame The main {@link JFrame} of the game.
     * @param panel The {@link GUIPanel} to display.
     */
    private void showGUIPanel(final JFrame frame, final GUIPanel panel) {
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
     * Displays the main gameplay screen.
     *
     * @param frame The main game frame.
     */
    public void showGameplayPanel(final JFrame frame) {
        showGUIPanel(frame, this.gameplayPanel);
    }

    /**
     * Displays the game modes selection screen.
     *
     * @param frame The main game frame.
     */
    public void showGameModesScreen(final JFrame frame) {
        showGUIPanel(frame, this.gameModesPanel);
    }

    /**
     * Displays the main menu screen.
     *
     * @param frame The main game frame.
     */
    public void showMenuScreen(final JFrame frame) {
        this.menuPanel.updateMenu();
        showGUIPanel(frame, this.menuPanel);
    }

    /**
     * Displays the settings configuration screen.
     *
     * @param frame The main game frame.
     */
    public void showSettingsScreen(final JFrame frame) {
        showGUIPanel(frame, this.settingPanel);
    }

    /**
     * Displays the victory screen and updates it with current score and level.
     *
     * @param frame The main game frame.
     */
    public void showWinScreen(final JFrame frame) {
        final GameManager gm = Game.getGame().getGm();
        this.winPanel.updateInfo(gm.getScore(), gm.getCurrentLevel());
        showGUIPanel(frame, this.winPanel);
    }

    /**
     * Displays the game over screen and updates it with current score and level.
     *
     * @param frame The main game frame.
     */
    public void showGameOverScreen(final JFrame frame) {
        final GameManager gm = Game.getGame().getGm();
        this.gameOverPanel.updateInfo(gm.getScore(), gm.getCurrentLevel());
        showGUIPanel(frame, this.gameOverPanel);
    }

    // --- Getters for Panels ---

    public GameplayPanel getGameplayPanel() {
        return this.gameplayPanel;
    }

    public SettingPanel getSettingPanel() {
        return this.settingPanel;
    }

    public GameModesPanel getGameModesPanel() {
        return this.gameModesPanel;
    }

    /**
     * Sets the state of the game prior to entering a temporary screen (like settings).
     *
     * @param state The game state (an integer ID).
     */
    public void setPreviousState(final int state) {
        this.previousState = state;
    }

    /**
     * Gets the state of the game prior to entering a temporary screen.
     *
     * @return The previous game state ID.
     */
    public int getPreviousState() {
        return this.previousState;
    }
}