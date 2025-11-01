package com.breakout.listeners;

import com.breakout.Game;
import com.breakout.managers.GameManager;
import com.breakout.managers.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private Game game;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameKeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameManager gm = game.getGm();
        GameState currentState = game.getState();

        // SPACE - Toggle Settings
        if (key == KeyEvent.VK_SPACE) {
            // Nếu đang ở Settings -> Quay về state trước đó
            if (currentState == GameState.SETTING) {
                GameState previous = game.getGUI().getPreviousState();
                if (previous != null) {
                    game.changeState(previous);
                }
                return;
            }

            // Nếu đang PLAYING -> Mở Settings
            if (currentState == GameState.PLAYING) {
                if (!gm.hasBallStarted()) {
                    gm.startBall();
                    return;
                }
                else {
                    game.changeState(GameState.SETTING);
                    return;
                }
            }
        }

        // Điều khiển paddle (chỉ khi đang PLAYING)
        if (currentState == GameState.PLAYING) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.getState() == GameState.PLAYING) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
}
