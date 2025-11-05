package com.breakout.listeners;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.config.GameConfig;
import com.breakout.managers.GameManager;
import com.breakout.managers.Level;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for gameplay and menu navigation.
 * <p>
 * This class listens for key presses and releases to control
 * the paddle, navigate between states, and trigger special actions.
 * </p>
 */
public class GameKeyListener implements KeyListener {

    /** Indicates whether the left key is currently pressed. */
    private boolean leftPressed = false;

    /** Indicates whether the right key is currently pressed. */
    private boolean rightPressed = false;

    /** Default constructor. */
    public GameKeyListener() {}

    /**
     * Handles key press events.
     *
     * @param e the {@link KeyEvent} triggered when a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameManager gm = Game.getGame().getGm();
        int currentState = Game.getGame().getState();

        // ESC - Exit without saving from gameplay or return from settings
        if (key == KeyEvent.VK_ESCAPE) {
            if (currentState == Defs.STATE_PLAYING) {
                Game.getGame().exitWithoutSaving();
                return;
            }
            if (currentState == Defs.STATE_SETTING) {
                int previous = Game.getGame().getGUI().getPreviousState();
                if (previous != Defs.STATE_LOADING) {
                    Game.getGame().changeState(previous);
                }
                return;
            }
        }

        // SPACE - Start ball or open settings
        if (key == KeyEvent.VK_SPACE) {
            // Start ball if in PLAYING state and ball not started
            if (currentState == Defs.STATE_PLAYING && !gm.hasBallStarted()) {
                gm.startBall();
                System.out.println("Ball started by SPACE!");
                return;
            }

            // Open settings if ball already started
            if (currentState == Defs.STATE_PLAYING && gm.hasBallStarted()) {
                Game.getGame().changeState(Defs.STATE_SETTING);
                return;
            }
        }

        // U - Unlock all levels (debug/testing shortcut)
        if (currentState == Defs.STATE_GAME_MODES && key == KeyEvent.VK_U) {
            for (int i = 1; i <= GameConfig.TOTAL_LEVELS; i++) {
                Level.unlockLevel(i);
            }
        }

        // Paddle movement (only in PLAYING state)
        if (currentState == Defs.STATE_PLAYING) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = true;
            }
        }
    }

    /**
     * Handles key release events.
     *
     * @param e the {@link KeyEvent} triggered when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.getGame().getState() == Defs.STATE_PLAYING) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = false;
            }
        }
    }

    /**
     * Resets all key states (used when re-entering gameplay or resetting controls).
     */
    public void resetKeys() {
        leftPressed = false;
        rightPressed = false;
    }

    /**
     * Not used in this implementation.
     *
     * @param e the {@link KeyEvent} triggered when a key is typed
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Checks whether the left key is currently pressed.
     *
     * @return {@code true} if the left key is pressed; {@code false} otherwise
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Checks whether the right key is currently pressed.
     *
     * @return {@code true} if the right key is pressed; {@code false} otherwise
     */
    public boolean isRightPressed() {
        return rightPressed;
    }
}
