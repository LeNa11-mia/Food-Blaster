package com.breakout;

import com.breakout.config.Defs;
import com.breakout.gui.GUIPanel;
import com.breakout.listeners.GameKeyListener;
import com.breakout.managers.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        SoundManager.loadSounds();

        // Thêm KeyListener cho GameplayPanel
        gui.getGameplayPanel().addKeyListener(keyListener);

        // THÊM DÒNG NÀY - KeyListener cho SettingPanel
        gui.getSettingPanel().addKeyListener(keyListener);

        // THÊM: Xử lý khi đóng cửa sổ - không lưu game
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Đóng game không lưu
                System.exit(0);
            }
        });
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
            SwingUtilities.invokeLater(() -> gui.getGameplayPanel().repaint());
            // Xử lý giao diện trên luồng riêng (EDT)
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
     * Bắt đầu game mới với difficulty được chọn
     */
    public void startNewGame(int difficulty) {
        gm.startGame(difficulty);
        changeState(Defs.STATE_PLAYING);
    }

    /**
     * Tiếp tục game từ save
     */
    public void startContinueGame() {
        if (gm.canContinueGame()) {
            gm.continueGame();
            changeState(Defs.STATE_PLAYING);
        } else {
            // Nếu không có game đã lưu, quay lại menu
            System.out.println("No saved game found!");
            changeState(Defs.STATE_MENU);
        }
    }

    /**
     * Thoát game không lưu - dùng khi ấn ESC từ gameplay
     */
    public void exitWithoutSaving() {
        // Reset game về trạng thái ban đầu
        gm.resetGame();
        changeState(Defs.STATE_MENU);
    }

    /**
     * Lưu game và thoát về menu - dùng từ SettingPanel
     */
    public void saveAndExitToMenu() {
        gm.saveCurrentGame();
        changeState(Defs.STATE_MENU);
    }

    /**
     * Kiểm tra xem có thể continue game không
     */
    public boolean canContinueGame() {
        return gm.canContinueGame();
    }

    /**
     * Lấy thông tin game đã lưu
     */
    public String getSaveInfo() {
        return gm.getSaveInfo();
    }

    public GameManager getGm() {
        return gm;
    }

    public GUIManager getGUI() {
        return gui;
    }

    public GameKeyListener getKeyListener() {
        return keyListener;
    }

    public JFrame getFrame() {
        return frame;
    }
}
