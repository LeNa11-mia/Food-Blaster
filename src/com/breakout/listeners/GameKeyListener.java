package com.breakout.listeners;

import com.breakout.Game;
import com.breakout.config.Defs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private Game game;

    public GameKeyListener(Game game) {
        this.game = game;
    }

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.getState() == Defs.STATE_PLAYING) {
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
        if (game.getState() == Defs.STATE_PLAYING) {
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

    public void resetKeys() {
        leftPressed = false;
        rightPressed = false;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
}
