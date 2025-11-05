package com.breakout.listeners;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.managers.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for gameplay and menu interaction.
 *
 * <p>This listener processes player movement inputs, game control actions,
 * and transitions between different game states. It tracks whether movement
 * keys are currently held down to allow smooth continuous paddle movement.</p>
 */
public class GameKeyListener implements KeyListener {

    /** Whether the left movement key is currently pressed. */
    private boolean leftPressed = false;

    /** Whether the right movement key is currently pressed. */
    private boolean rightPressed = false;

    /** Creates a new keyboard input listener for game controls. */
    public GameKeyListener() {}

    /**
     * Called when a key is pressed down. Handles game state transitions,
     * starting gameplay, opening settings, and paddle movement.
     *
     * @param e the key event containing key code information
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameManager gm = Game.getGame().getGm();
        int currentState = Game.getGame().getState();

        // ESC — Exit to previous screen or quit gameplay without saving
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

        // SPACE — Continue, start game, start ball, or open settings
        if (key == KeyEvent.VK_SPACE) {

            // From main menu → Continue or go to Game Modes
            if (currentState == Defs.STATE_MENU) {
                if (Game.getGame().canContinueGame()) {
                    Game.getGame().startContinueGame();
                } else {
                    Game.getGame().changeState(Defs.STATE_GAME_MODES);
                }
                return;
            }

            // Selecting Easy mode in Game Modes screen
            if (currentState == Defs.STATE_GAME_MODES) {
                Game.getGame().startNewGame(Defs.LEVEL_EASY);
                return;
            }

            // Start the ball if gameplay is running but ball hasn't started
            if (currentState == Defs.STATE_PLAYING && !gm.hasBallStarted()) {
                gm.startBall();
                System.out.println("Ball started by SPACE!");
                return;
            }

            // If ball has started → open settings
            if (currentState == Defs.STATE_PLAYING && gm.hasBallStarted()) {
                Game.getGame().changeState(Defs.STATE_SETTING);
                return;
            }
        }

        // Paddle movement during gameplay
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
     * Called when a key is released. Stops paddle movement when movement keys are released.
     *
     * @param e the key event containing key code information
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
     * Clears movement key states, typically used when resetting gameplay.
     */
    public void resetKeys() {
        leftPressed = false;
        rightPressed = false;
    }

    /** Not used, but required by the KeyListener interface. */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * @return true if the left movement key is currently pressed.
     */
    public boolean isLeftPressed() { return leftPressed; }

    /**
     * @return true if the right movement key is currently pressed.
     */
    public boolean isRightPressed() { return rightPressed; }
}
