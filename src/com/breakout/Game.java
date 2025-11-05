package com.breakout;

import com.breakout.config.Defs;
import com.breakout.gui.GUIPanel;
import com.breakout.listeners.GameKeyListener;
import com.breakout.managers.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * {@code Game} is the main game controller, implementing the Singleton pattern.
 * It manages the game loop, state transitions, and centralizes access to all managers.
 */
public class Game {
    private static Game instance = null;

    private final JFrame frame;

    private final GameManager gm;
    private final GUIManager gui;
    private final GameKeyListener keyListener;

    // Game state and loop control
    private int state;
    private Thread gameThread;
    private volatile boolean running;

    // Timing
    private long lastTime;

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param frame The main {@link JFrame} window.
     */
    private Game(final JFrame frame) {
        this.state = Defs.STATE_LOADING;
        this.frame = frame;
        // Ensure instance is set before initializing managers that might reference it
        instance = this;

        // Initialize managers
        this.gm = new GameManager();
        this.gui = new GUIManager();
        this.keyListener = new GameKeyListener();

        // Load all game sounds
        SoundManager.loadSounds();

        // Add KeyListener to relevant panels
        this.gui.getGameplayPanel().addKeyListener(this.keyListener);
        this.gui.getSettingPanel().addKeyListener(this.keyListener);
        this.gui.getGameModesPanel().addKeyListener(this.keyListener);

        // ADDED: Handle window closing action
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                // Exit the game without saving
                System.exit(0);
            }
        });
    }

    /**
     * Initializes the Singleton instance of the Game controller.
     * This must be called exactly once at startup.
     *
     * @param frame The main {@link JFrame} window.
     */
    public static void initGame(final JFrame frame) {
        instance = new Game(frame);
        System.out.println("Game initialized!");
    }

    /**
     * Provides the single, global instance of the Game controller.
     *
     * @return The {@link Game} instance.
     */
    public static Game getGame() {
        if (instance != null) {
            return instance;
        } else {
            System.err.println("Game not initialized! Exiting.");
            System.exit(0);
            return null; // Should be unreachable
        }
    }

    /**
     * Starts the main game loop in a separate thread.
     */
    private void startGameLoop() {
        if (this.gameThread != null && this.gameThread.isAlive()) {
            return; // Already running, prevent multiple loops
        }

        // The argument is a lambda expression that defines the code the thread will run.
        this.gameThread = new Thread(() -> {
            while (this.running) {
                update();
                // This try-catch handles the rare case that the thread is interrupted during sleep
                try {
                    // ~60 FPS: Pauses the loop for about 16 milliseconds to limit CPU usage.
                    Thread.sleep(16);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.gameThread.start();
    }

    /**
     * Initializes timing and starts the game loop, switching the state to the Menu.
     */
    public void start() {
        this.lastTime = System.nanoTime();
        this.running = true;

        changeState(Defs.STATE_MENU);

        // Start game loop
        startGameLoop();
    }

    /**
     * The core update logic of the game loop.
     * Calculates delta time and updates the game state if currently playing.
     */
    private void update() {
        if (!this.running || this.gm == null) return;

        // Calculate deltaTime
        final long currentTime = System.nanoTime();
        final double deltaTime = (currentTime - this.lastTime) / 1_000_000_000.0;
        this.lastTime = currentTime;

        if (this.state == Defs.STATE_PLAYING) {
            // Update game logic (movement, collision, scores)
            this.gm.update(deltaTime, this.keyListener.isLeftPressed(), this.keyListener.isRightPressed());

            // Handle GUI drawing on the Event Dispatch Thread (EDT)
            SwingUtilities.invokeLater(() -> this.gui.getGameplayPanel().repaint());
        }
    }

    /**
     * Gets the current state of the game.
     *
     * @return The current state ID (integer from {@link Defs}).
     */
    public int getState() {
        return this.state;
    }

    /**
     * Changes the current game state and updates the GUI accordingly.
     *
     * @param state The new state ID (integer from {@link Defs}).
     */
    public void changeState(final int state) {
        if (this.state == state) return;

        // Store the previous state if moving into settings
        if (state == Defs.STATE_SETTING && this.state != Defs.STATE_LOADING) {
            this.gui.setPreviousState(this.state);
        }

        this.state = state;
        switch (state) {
            case Defs.STATE_MENU:
                this.gui.resetButton(GUIPanel.originalColors);
                this.gui.showMenuScreen(this.frame);
                break;
            case Defs.STATE_PLAYING:
                this.keyListener.resetKeys();
                this.gui.showGameplayPanel(this.frame);
                break;
            case Defs.STATE_GAME_MODES:
                this.gui.resetButton(GUIPanel.originalColors);
                this.gui.showGameModesScreen(this.frame);
                break;
            case Defs.STATE_SETTING:
                this.gui.resetButton(GUIPanel.originalColors);
                this.gui.showSettingsScreen(this.frame);
                break;
            case Defs.STATE_WIN:
                this.gui.resetButton(GUIPanel.originalColors);
                this.gui.showWinScreen(this.frame);
                break;
            case Defs.STATE_GAMEOVER:
                this.gui.resetButton(GUIPanel.originalColors);
                this.gui.showGameOverScreen(this.frame);
                break;
        }
    }

    /**
     * Attempts to continue the game from a saved file.
     */
    public void startContinueGame() {
        if (this.gm.canContinueGame()) {
            this.gm.continueGame();
            changeState(Defs.STATE_PLAYING);
        } else {
            // If no saved game found, return to menu
            System.out.println("No saved game found!");
            changeState(Defs.STATE_MENU);
        }
    }

    /**
     * Exits the current gameplay session without saving and returns to the menu.
     * Used when pressing ESC during gameplay.
     */
    public void exitWithoutSaving() {
        changeState(Defs.STATE_MENU);
    }

    /**
     * Saves the current game state and returns to the menu.
     * Used from the Setting Panel.
     */
    public void saveAndExitToMenu() {
        this.gm.saveCurrentGame();
        changeState(Defs.STATE_MENU);
    }

    // --- Getters for Managers and Components ---

    public GameManager getGm() {
        return this.gm;
    }

    public GUIManager getGUI() {
        return this.gui;
    }

    public GameKeyListener getKeyListener() {
        return this.keyListener;
    }
}