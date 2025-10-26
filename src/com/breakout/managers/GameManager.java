package com.breakout.managers;

import com.breakout.Game;
import com.breakout.Main;
import com.breakout.entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Gameplay manager
 */
public class GameManager {
    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private Rectangle gameArea;

    private int score;
    private int lives;
    private String currentDifficulty = "EASY";
    private boolean laserEnabled;
    private boolean gameOver;

    public GameManager() {
        // Initialize game objects
        gameArea = new Rectangle(0, 0, Main.WIDTH-15, Main.HEIGHT);
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2, gameArea);
        paddle = new Paddle(Main.WIDTH/2 - 50, Main.HEIGHT - 50);
        bricks = new ArrayList<>();
        lives = 1; // Only 1 life as per your requirement
        gameOver = false;
    }

    public void startGame(String difficulty) {
        if (difficulty.equals("EASY")) {
            bricks = Level.loadLevel(1);
        } else if (difficulty.equals("MEDIUM")) {
            bricks = Level.loadLevel(2);
        } else if (difficulty.equals("HARD")) {
            bricks = Level.loadLevel(3);
        } else if (difficulty.equals("BOSS")) {
            bricks = Level.loadLevel(3);
        }
        resetBall();
        resetPaddle();
        lives = 1;
        score = 0;
        gameOver = false;
    }

    public void update(Game game, double deltaTime, boolean leftPressed, boolean rightPressed) {
        if (isGameOver()) {
            game.changeState(GameState.GAMEOVER);
            return; // Don't update if game is over
        } else if (isWin()) {
            game.changeState(GameState.WIN);
            return;
        }

        // Update ball
        ball.update(deltaTime);

        // Control paddle
        if (leftPressed) {
            paddle.moveLeft(deltaTime, Main.WIDTH);
        }
        if (rightPressed) {
            paddle.moveRight(deltaTime, Main.WIDTH);
        }

        // Ball falls below bottom border - GAME OVER
        if (ball.getY() > Main.HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                // Reset ball if still have lives (though you have only 1 life)
                resetBall();
            }
        }

        // Collision with paddle
        if (ball.intersects(paddle) && ball.getVy() > 0) {
            ball.bounceY();
        }

        // Collision with bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.intersects(brick)) {
                brick.hit();
                ball.bounceY();
                score += 1;
                break; // Only destroy one brick per collision
            }
        }
    }

    public String getNextDifficulty() {
        switch (currentDifficulty) {
            case "EASY": return "MEDIUM";
            case "MEDIUM": return "HARD";
            case "HARD": return "BOSS";
            case "BOSS": return null;
            default: return null;
        }
    }

    private void resetBall() {
        ball = new Ball(Main.WIDTH/2, Main.HEIGHT/2, gameArea);
    }

    private void resetPaddle() {
        paddle = new Paddle(Main.WIDTH/2 - 50, Main.HEIGHT - 50);
    }

    private void spawnRandomItem(double x, double y) {
    }

    public void addExtraBall() {
    }

    public void enableLaser() {
        laserEnabled = true;
        // Laser shooting logic:
    }

    private void nextLevel() {
    }

    // Check if player won (all bricks destroyed)
    public boolean isWin() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Brick> getBricks() { return bricks; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public String getDifficulty() { return currentDifficulty; }
    public boolean isGameOver() { return gameOver; }
}
