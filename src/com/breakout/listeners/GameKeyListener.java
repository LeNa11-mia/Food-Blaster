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

        // ESC - Thoát không lưu từ gameplay
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

        // SPACE - Continue hoặc chọn mode vào game, và start ball
        if (key == KeyEvent.VK_SPACE) {
            if (currentState == Defs.STATE_MENU) {
                // Nếu có game đã lưu -> Continue, ngược lại -> New Game
                if (Game.getGame().canContinueGame()) {
                    Game.getGame().startContinueGame();
                } else {
                    Game.getGame().changeState(Defs.STATE_GAME_MODES);
                }
                return;
            }

            // Nếu đang ở Game Modes -> Chọn Easy level
            if (currentState == Defs.STATE_GAME_MODES) {
                Game.getGame().startNewGame(Defs.LEVEL_EASY);
                return;
            }

            // Nếu đang PLAYING và ball chưa bắt đầu -> start ball
            if (currentState == Defs.STATE_PLAYING && !gm.hasBallStarted()) {
                gm.startBall();
                System.out.println("Ball started by SPACE!");
                return;
            }

            // Nếu đang PLAYING và ball đã bắt đầu -> vào setting
            if (currentState == Defs.STATE_PLAYING && gm.hasBallStarted()) {
                Game.getGame().changeState(Defs.STATE_SETTING);
                return;
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
