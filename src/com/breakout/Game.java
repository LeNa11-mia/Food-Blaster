package com.breakout;

import com.breakout.config.Defs;
import com.breakout.gui.GUIPanel;
import com.breakout.listeners.GameKeyListener;
import com.breakout.managers.*;

import javax.swing.*;

/**
 * Game controller, using Singleton
 */
public class Game {
    private static Game instance = null;

    private JFrame frame;

    private GameManager gm;
    private GUIManager gui;
    private GameKeyListener keyListener;

    // Game state
    private int state;
    private Thread gameThread;
    private boolean running;

    // Timing
    private long lastTime;

    private Game(JFrame frame) {
        state = Defs.STATE_LOADING;
        this.frame = frame;
        instance = this; // Đảm bào instance không null trước khi khởi tạo gm và gui
        gm = new GameManager();
        gui = new GUIManager();
        keyListener = new GameKeyListener();

        // Thêm KeyListener cho GameplayPanel
        gui.getGameplayPanel().addKeyListener(keyListener);

        // THÊM DÒNG NÀY - KeyListener cho SettingPanel
        gui.getSettingPanel().addKeyListener(keyListener);
    }

    public static void initGame(JFrame frame) {
        instance = new Game(frame);
        System.out.println("Đã khởi tạo Game!");
    }

    public static Game getGame() {
        if (instance != null) {
            return instance;
        } else {
            System.out.println("Chưa khởi tạo Game!");
            System.exit(0);
            return null;
        }
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

        changeState(Defs.STATE_MENU);

        // Start game loop
        startGameLoop();
    }

    private void update() {
        if (!running || gm == null) return;

        // Calculate deltaTime
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;

        if (state == Defs.STATE_PLAYING) {
            gm.update(deltaTime, keyListener.isLeftPressed(), keyListener.isRightPressed());
            gui.getGameplayPanel().repaint();
        }
    }

    public int getState() {
        return state;
    }

    public void changeState(int state) {
        if (this.state == state) return;

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
            case Defs.STATE_GAME_MODES:  // THÊM CASE NÀY
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
