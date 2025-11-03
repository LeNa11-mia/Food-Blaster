package com.breakout.listeners;

import com.breakout.Game;
import com.breakout.config.Defs;
import com.breakout.managers.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameKeyListener() {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameManager gm = Game.getGame().getGm();
        int currentState = Game.getGame().getState();

        // SPACE - Toggle Settings
        if (key == KeyEvent.VK_SPACE) {
            // Nếu đang ở Settings -> Quay về state trước đó
            if (currentState == Defs.STATE_SETTING) {
                int previous = Game.getGame().getGUI().getPreviousState();
                if (previous != Defs.STATE_LOADING) {
                    Game.getGame().changeState(previous);
                }
                return;
            }

            // Nếu đang PLAYING -> Mở Settings
            if (currentState == Defs.STATE_PLAYING) {
                if (!gm.hasBallStarted()) {
                    gm.startBall();
                    return;
                }
                else {
                    Game.getGame().changeState(Defs.STATE_SETTING);
                    return;
                }
            }
        }

        // Điều khiển paddle (chỉ khi đang PLAYING)
        if (currentState == Defs.STATE_PLAYING) {
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
        if (Game.getGame().getState() == Defs.STATE_PLAYING) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                rightPressed = false;
            }
        }
    }

    public void resetKeys() {
        leftPressed = false;
        rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
}
