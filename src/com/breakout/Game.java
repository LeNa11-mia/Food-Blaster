package com.breakout;

import com.breakout.config.Defs;
import com.breakout.gui.GUIPanel;
import com.breakout.listeners.GameKeyListener;
import com.breakout.managers.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The central controller of the game, implemented as a Singleton.
 *
 * <p>This class manages:
 * <ul>
 *     <li>Game state transitions</li>
 *     <li>Main game loop</li>
 *     <li>Access to managers (GUIManager, GameManager, SoundManager)</li>
 *     <li>Input listener binding</li>
 * </ul>
 * Only one instance of the game can exist at a time.</p>
 *
 * @author Breakout Team
 * @version 1.0
 */
public class Game {
    /** Singleton instance of the Game class */
    private static Game instance = null;

    /** Main application window */
    private JFrame frame;

    /** Manages game logic and state */
    private GameManager gm;

    /** Manages user interface panels and navigation */
    private GUIManager gui;

    /** Handles keyboard input for game controls */
    private GameKeyListener keyListener;

    /** Current game state (menu, playing, game over, etc.) */
    private int state;

    /** Thread running the main game loop */
    private Thread gameThread;

    /** Flag indicating if the game is currently running */
    private boolean running;

    /** Timing variable for update loop calculations */
    private long lastTime;

    /**
     * Private constructor for Singleton pattern.
     * Initializes all game managers and sets up the game environment.
     *
     * @param frame the main application window
     */
    private Game(JFrame frame) {
        state = Defs.STATE_LOADING;
        this.frame = frame;

        // Ensure the static instance is available before other managers reference it
        instance = this;

        gm = new GameManager();
        gui = new GUIManager();
        keyListener = new GameKeyListener();

        SoundManager.loadSounds();

        // Attach input to primary view panels
        gui.getGameplayPanel().addKeyListener(keyListener);
        gui.getSettingPanel().addKeyListener(keyListener);

        // Ensure closing the window exits without saving
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Handles window closing event to ensure proper application termination.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Initializes the Singleton instance of the game.
     * Must be called before any other game operations.
     *
     * @param frame the JFrame used as the game window
     */
    public static void initGame(JFrame frame) {
        instance = new Game(frame);
        System.out.println("Game initialized!");
    }

    /**
     * Returns the current game instance.
     * Exits application if game hasn't been initialized.
     *
     * @return the current Game instance
     */
    public static Game getGame() {
        if (instance != null) {
            return instance;
        }
        System.out.println("Game has not been initialized!");
        System.exit(0);
        return null;
    }

    /**
     * Starts the continuous update/render loop (approx. 60 FPS).
     * Prevents multiple loops from being started accidentally.
     */
    private void startGameLoop() {
        if (gameThread != null && gameThread.isAlive()) {
            return;
        }

        gameThread = new Thread(() -> {
            while (running) {
                update();
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    /**
     * Starts the game and begins the main loop.
     * Initializes timing and transitions to the menu state.
     */
    public void start() {
        lastTime = System.nanoTime();
        running = true;
        changeState(Defs.STATE_MENU);
        startGameLoop();
    }

    /**
     * Performs per-frame updates and calls manager updates when in PLAYING state.
     * Calculates delta time and delegates gameplay updates to GameManager.
     */
    private void update() {
        if (!running || gm == null) return;

        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        if (state == Defs.STATE_PLAYING) {
            gm.update(deltaTime, keyListener.isLeftPressed(), keyListener.isRightPressed());
            SwingUtilities.invokeLater(() -> gui.getGameplayPanel().repaint());
        }
    }

    /**
     * Gets the current game state.
     *
     * @return the current state constant from Defs
     */
    public int getState() {
        return state;
    }

    /**
     * Changes the active game state and updates the displayed GUI panel accordingly.
     * Handles state transitions and manages the previous state for navigation.
     *
     * @param state new game state constant from Defs
     */
    public void changeState(int state) {
        if (this.state == state) return;

        // Store previous state when entering settings (except from loading)
        if (state == Defs.STATE_SETTING && this.state != Defs.STATE_LOADING) {
            gui.setPreviousState(this.state);
        }

        this.state = state;
        switch (state) {
            case Defs.STATE_MENU:
                gui.resetButton(GUIPanel.originalColors);
                gui.showMenuScreen(frame);
                break;
            case Defs.STATE_PLAYING:
                keyListener.resetKeys();
                gui.showGameplayPanel(frame);
                break;
            case Defs.STATE_GAME_MODES:
                gui.resetButton(GUIPanel.originalColors);
                gui.showGameModesScreen(frame);
                break;
            case Defs.STATE_SETTING:
                gui.resetButton(GUIPanel.originalColors);
                gui.showSettingsScreen(frame);
                break;
            case Defs.STATE_WIN:
                gui.resetButton(GUIPanel.originalColors);
                gui.showWinScreen(frame);
                break;
            case Defs.STATE_GAMEOVER:
                gui.resetButton(GUIPanel.originalColors);
                gui.showGameOverScreen(frame);
                break;
        }
    }

    /**
     * Starts a new game session using the selected difficulty.
     * Initializes the game manager with the specified level and begins gameplay.
     *
     * @param difficulty a constant defined in {@link Defs}
     */
    public void startNewGame(int difficulty) {
        gm.startGame(difficulty);
        changeState(Defs.STATE_PLAYING);
    }

    /**
     * Continues a previously saved game if available.
     * Loads saved state from disk and resumes gameplay.
     */
    public void startContinueGame() {
        if (gm.canContinueGame()) {
            gm.continueGame();
            changeState(Defs.STATE_PLAYING);
        } else {
            System.out.println("No saved game found!");
            changeState(Defs.STATE_MENU);
        }
    }

    /**
     * Exits the current game session without saving progress.
     * Used when the player presses ESC during gameplay.
     * Resets game state and returns to main menu.
     */
    public void exitWithoutSaving() {
        gm.resetGame();
        changeState(Defs.STATE_MENU);
    }

    /**
     * Saves the current game state and returns to the main menu.
     * Persists game progress to disk before navigating away.
     */
    public void saveAndExitToMenu() {
        gm.saveCurrentGame();
        changeState(Defs.STATE_MENU);
    }

    /**
     * Checks whether a saved game exists that can be continued.
     *
     * @return true if a game can be continued, false otherwise
     */
    public boolean canContinueGame() {
        return gm.canContinueGame();
    }

    /**
     * Returns save file information for display in the UI.
     * Provides formatted details about the saved game state.
     *
     * @return formatted save information string
     */
    public String getSaveInfo() {
        return gm.getSaveInfo();
    }

    // ----------- Getters for Manager Access -----------

    /**
     * Gets the GameManager instance for game logic operations.
     *
     * @return GameManager the game logic manager
     */
    public GameManager getGm() { return gm; }

    /**
     * Gets the GUIManager instance for UI operations.
     *
     * @return GUIManager the user interface manager
     */
    public GUIManager getGUI() { return gui; }

    /**
     * Gets the GameKeyListener instance for input handling.
     *
     * @return GameKeyListener the keyboard input listener
     */
    public GameKeyListener getKeyListener() { return keyListener; }

    /**
     * Gets the main application window.
     *
     * @return JFrame the main game window
     */
    public JFrame getFrame() { return frame; }
}
