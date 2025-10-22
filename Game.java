package com.breakout;

import com.breakout.gui.GameplayPanel;
import com.breakout.listeners.GameKeyListener;
import com.breakout.managers.*;

import javax.swing.*;

/**
 * Game controller
 */
public class Game {
    private JFrame frame;

    private GameManager gm;
    private GUIManager gui;
    private GameKeyListener keyListener;

    // Game state
    private GameState state;
    private Thread gameThread;
    private boolean running;

    // Timing
    private long lastTime;

    public Game(JFrame frame) {
        this.frame = frame;
        gm = new GameManager();
        gui = new GUIManager(this);
        keyListener = new GameKeyListener(this);
        state = null;
        gui.getGameplayPanel().addKeyListener(keyListener); // Only gameplay panel get keyboard input
    }

    private void startGameLoop() {
        if (gameThread != null && gameThread.isAlive()) {
            return; // Already running, preempt multiple loops
        }

        // ( explain for co-members: The argument is a lambda expression (() -> { ... })
        // which defines the code that the thread will run.
        gameThread = new Thread(() -> {
            while (running) {
                update();
                // this try-catch handles the rare case that the thread is interrupted during sleep
                try {
                    // ~60 FPS, pauses the loop for about 16 milliseconds.
                    //1000 ms / 16 ms ≈ 60 updates per second, ~ 60 frames per second,
                    // keeps the loop from running too fast and burning CPU unnecessarily.
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void start() {
        lastTime = System.nanoTime();
        running = true;

        changeState(GameState.MENU);

        // Start game loop
        startGameLoop();
    }

    private void update() {
        if (!running || gm == null) return;

        // Calculate deltaTime
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        if (state == GameState.PLAYING) {
            gm.update(this, deltaTime, keyListener.isLeftPressed(), keyListener.isRightPressed());
            gui.getGameplayPanel().repaint();
        }
    }

    public GameState getState() {
        return state;
    }

    public void changeState(GameState state) {
        if (this.state == state) return;

        this.state = state;
        switch (state) {
            case MENU:
                gui.showMenuScreen(frame);
                break;
            case PLAYING:
                gui.showGameplayPanel(frame);
                break;
            case WIN:
                gui.showWinScreen(frame);
                break;
            case GAMEOVER:
                gui.showGameOverScreen(frame);
                break;
        }
    }

    public GameManager getGm() {
        return gm;
    }

    public GUIManager getGUI() {
        return gui;
    }

    public JFrame getFrame() {
        return frame;
    }
}
